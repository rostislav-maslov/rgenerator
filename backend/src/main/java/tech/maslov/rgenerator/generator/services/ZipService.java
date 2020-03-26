package tech.maslov.rgenerator.generator.services;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.ub.core.file.services.FileService;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maslov.rgenerator.generator.models.FileStructure;
import tech.maslov.rgenerator.generator.models.GeneratorDoc;
import tech.maslov.rgenerator.templateResult.models.TemplateResultDoc;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipService {
    public static final String ROOT_DIR = "generatorContent";

    @Autowired
    private FileService fileService;

    public TemplateResultDoc generate(GeneratorDoc generatorDoc, TemplateResultDoc templateResultDoc) throws IOException {
        File directoryTemplate = getDirForObject(templateResultDoc.getId());
        File directoryForContent = getDirForGenerateFiles(directoryTemplate);

        Map data = contentObject(templateResultDoc.getContent());

        generateDir(directoryForContent.getAbsolutePath(), generatorDoc.getFileStructure().getDirectory(), data);

        String zipFilePath = directoryTemplate.getAbsolutePath() + "/" + "templateResult.zip";
        File zipFile = generateZip(directoryForContent, zipFilePath);

        GridFSFile gridFSFile = fileService.save(zipFile);
        templateResultDoc.setResultFileId((ObjectId) gridFSFile.getId());

        cleanData(directoryTemplate);

        return templateResultDoc;
    }

    private void cleanData(File directoryTemplate){
        directoryTemplate.delete();
    }

    private void generateDir(String parentDirFullPath, FileStructure.Directory directory, Map data) {
        String dirFullPath = directory.getName();
        if (parentDirFullPath != null) dirFullPath = parentDirFullPath + "/" + dirFullPath;

        File dirFile = new File(dirFullPath);
        if (dirFile.exists() == false) dirFile.mkdir();

        for (FileStructure.File file : directory.getFiles()) {
            try {
                String content = getStringContentFromGridFS(file.getFileId());
                createFile(dirFullPath, file.getName(), content, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (FileStructure.Directory childDirectory : directory.getDirectories()) {
            generateDir(dirFullPath, childDirectory, data);
        }
    }

    private String getStringContentFromGridFS(ObjectId id) throws IOException {
        GridFSDBFile gridFSFile = fileService.getFile(id);

        StringWriter writer = new StringWriter();
        String encoding = StandardCharsets.UTF_8.name();
        IOUtils.copy(gridFSFile.getInputStream(), writer, encoding);

        return writer.toString();
    }

    /**
     * Генерируем объект для темплейта
     *
     * @param content
     * @return
     * @throws IOException
     */
    private Map contentObject(String content) throws IOException {
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
    private File getDirForObject(ObjectId templateResultId) {
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
    private String genTemplate(Map data, String template) throws IOException {
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

        File file = new File(fullPath);
        if (file.exists()) file.delete();

        String templateResult = genTemplate(data, template);

        BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true));
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
