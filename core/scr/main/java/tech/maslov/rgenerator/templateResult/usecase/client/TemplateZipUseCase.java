package tech.maslov.rgenerator.templateResult.usecase.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.file.port.FileRepository;
import com.rcore.domain.file.port.FileStorage;
import org.apache.commons.io.IOUtils;
import tech.maslov.rgenerator.generator.entity.FileStructure;
import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.templateResult.port.TemplateResultRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TemplateZipUseCase extends TemplateResultBaseUseCase {
    public static final String ROOT_DIR = "generatorContent";
    public final FileRepository fileRepository;
    public final FileStorage fileStorage;

    public TemplateZipUseCase(TemplateResultRepository templateResultRepository, FileRepository fileRepository, FileStorage fileStorage) {
        super(templateResultRepository);
        this.fileRepository = fileRepository;
        this.fileStorage = fileStorage;
    }


    public TemplateResultEntity generate(GeneratorEntity generatorEntity, TemplateResultEntity templateResultEntity) throws IOException {
        File directoryTemplate = getDirForObject(templateResultEntity.getId());
        File directoryForContent = getDirForGenerateFiles(directoryTemplate);

        Map data = contentObject(templateResultEntity.getContent());

        generateDir(directoryForContent.getAbsolutePath(), generatorEntity.getFileStructure().getDirectory(), data);

        String zipFilePath = directoryTemplate.getAbsolutePath() + "/" + "templateResult.zip";
        File zipFile = generateZip(directoryForContent, zipFilePath);



        String zipFileId = fileStorage.store(new FileInputStream(zipFile), zipFile.getName(), "application/zip");
        templateResultEntity.setResultFileId(zipFileId);

        cleanData(directoryTemplate);

        return templateResultEntity;
    }

    private void cleanData(File directoryTemplate){
        directoryTemplate.delete();
    }

    private void generateDir(String parentDirFullPath, FileStructure.Directory directory, Map data) {
        String dirFullPath = directory.getName();
        if (parentDirFullPath != null) dirFullPath = parentDirFullPath + "/" + dirFullPath;

        String dirFullPathTemplate= dirFullPath;

        try {
            dirFullPathTemplate = genTemplate(data, dirFullPath);
        }catch (Exception ignore){}

        File dirFile = new File(dirFullPathTemplate);
        if (dirFile.exists() == false) dirFile.mkdir();

        for (FileStructure.File file : directory.getFiles()) {
            try {
                String content = getStringContentFromGridFS(file.getFileId());
                createFile(dirFullPathTemplate, file.getName(), content, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (FileStructure.Directory childDirectory : directory.getDirectories()) {
            generateDir(dirFullPathTemplate, childDirectory, data);
        }
    }

    private String getStringContentFromGridFS(String id) throws IOException {
        FileEntity fileEntity = fileRepository.findById(id).get();

        StringWriter writer = new StringWriter();
        String encoding = StandardCharsets.UTF_8.name();
        IOUtils.copy(fileStorage.getInputStream(fileEntity.getId()).get(), writer, encoding);

        return writer.toString();
    }

    /**
     * Генерируем объект для темплейта
     *
     * @param content
     * @return
     * @throws IOException
     */
    public Map contentObject(String content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, Map.class);
    }

    /**
     * Рут директория
     *
     * @return
     */
    private File getRootDir() {
        File dir = new File(ROOT_DIR);
        if (dir.exists() == true) return dir;

        dir.mkdir();
        return dir;
    }

    /**
     * Директория для данного генератора
     *
     * @param templateResultId
     * @return
     */
    private File getDirForObject(String templateResultId) {
        File root = getRootDir();
        String path = root.getAbsolutePath() + "/" + templateResultId.toString();

        File genDir = new File(path);
        if (genDir.exists() == true) {
            genDir.delete();
        }

        genDir = new File(path);
        genDir.mkdir();
        return genDir;
    }

    /**
     * Директория в которой будем создавать файлы
     *
     * @param dirForObject
     * @return
     */
    private File getDirForGenerateFiles(File dirForObject) {
        String path = dirForObject.getAbsolutePath() + "/result";

        File genDir = new File(path);
        if (genDir.exists() == true) {
            genDir.delete();
        }

        genDir = new File(path);
        genDir.mkdir();
        return genDir;
    }

    /**
     * Получить контент из темплейта
     *
     * @param data
     * @param template
     * @return
     * @throws IOException
     */
    public String genTemplate(Map data, String template) throws IOException {
        StringWriter stringWriter = new StringWriter();

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(template), "template.mustache");
        mustache.execute(stringWriter, data).flush();

        String result = stringWriter.toString();
        stringWriter.flush();
        return result;
    }

    /**
     * Генерируем файл с контентом
     *
     * @param dir
     * @param nameFile
     * @param template
     * @param data
     * @throws IOException
     */
    private void createFile(String dir, String nameFile, String template, Map data) throws IOException {
        String fullPath = dir + "/" + genTemplate(data, nameFile);

        String fullPathWithData = genTemplate(data, fullPath);

        File file = new File(fullPathWithData);
        if (file.exists()) file.delete();

        String templateResult = genTemplate(data, template);

        BufferedWriter writer = new BufferedWriter(new FileWriter(fullPathWithData, true));
        writer.append(templateResult);
        writer.close();
    }

    private File generateZip(File dirForObjects, String zipFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFilePath);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        zipFile(dirForObjects, dirForObjects.getName(), zipOut);
        zipOut.close();
        fos.close();

        File zip = new File(zipFilePath);
        return zip;
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }


}
