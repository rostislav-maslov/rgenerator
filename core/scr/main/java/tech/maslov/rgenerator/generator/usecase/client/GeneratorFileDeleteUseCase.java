package tech.maslov.rgenerator.generator.usecase.client;

import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.file.port.FileRepository;
import tech.maslov.rgenerator.generator.entity.FileStructure;
import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;

import java.util.ArrayList;
import java.util.List;

public class GeneratorFileDeleteUseCase extends GeneratorBaseUseCase {
    private final FileRepository fileRepository;

    public GeneratorFileDeleteUseCase(GeneratorRepository generatorRepository, FileRepository fileRepository) {
        super(generatorRepository);
        this.fileRepository = fileRepository;
    }

    private void fileStructDeleteFile(FileStructure.Directory dir, String oldId) {
        List<FileStructure.File> toDelete = new ArrayList<>();

        for (FileStructure.File file : dir.getFiles()) {
            if (file.getFileId().equals(oldId)) {
                FileEntity fileEntity = fileRepository.findById(oldId).get();
                fileRepository.delete(fileEntity);
                toDelete.add(file);
            }
        }
        dir.getFiles().removeAll(toDelete);

        for (FileStructure.Directory childDir : dir.getDirectories()) {
            fileStructDeleteFile(childDir, oldId);
        }
    }

    public GeneratorEntity remove(String id, String fileId) {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();

        fileStructDeleteFile(generatorEntity.getFileStructure().getDirectory(), fileId);
        generatorRepository.save(generatorEntity);

        return generatorEntity;
    }
}
