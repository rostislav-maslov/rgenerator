package tech.maslov.rgenerator.generator.usecase.client;

import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.file.port.FileRepository;
import org.apache.commons.io.IOUtils;
import tech.maslov.rgenerator.generator.entity.FileStructure;
import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GeneratorViewUseCase extends GeneratorBaseUseCase {
    private final FileRepository fileRepository;

    public GeneratorViewUseCase(GeneratorRepository generatorRepository, FileRepository fileRepository) {
        super(generatorRepository);
        this.fileRepository = fileRepository;
    }

    private List<FileStructure.File> fileList(FileStructure.Directory dir) {
        List<FileStructure.File> response = new ArrayList<>();

        for (FileStructure.File file : dir.getFiles()) {
            FileStructure.File responseFile = new FileStructure.File();
            responseFile.setFileId(file.getFileId());

            response.add(responseFile);
        }

        for (FileStructure.Directory childDir : dir.getDirectories()) {
            response.addAll(
                    fileList(childDir)
            );
        }

        return response;
    }

    private FileStructure.File getFile(FileStructure.Directory dir, String fileId) {
        List<FileStructure.File> files = fileList(dir);
        for (FileStructure.File file : files) {
            if (file.getFileId().equals(fileId)) {
                return file;
            }
        }

        return null;
    }

    public FileEntity fileView(String id, String fileId) {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();
        FileStructure.File response = getFile(generatorEntity.getFileStructure().getDirectory(), fileId);
        if (response == null) return null;

        FileEntity file = fileRepository.findById(fileId).get();

        return file;
    }

    public GeneratorEntity findId(String id) {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();
        return generatorEntity;
    }

    public List<GeneratorEntity> findAll() {
        List<GeneratorEntity> generatorDocs = generatorRepository.find(10000l, 0l).getItems();

        return generatorDocs;
    }
}
