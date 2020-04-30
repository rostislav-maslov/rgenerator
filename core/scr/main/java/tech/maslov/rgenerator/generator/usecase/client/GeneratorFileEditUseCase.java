package tech.maslov.rgenerator.generator.usecase.client;

import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.file.port.FileRepository;
import tech.maslov.rgenerator.generator.entity.FileStructure;
import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;

public class GeneratorFileEditUseCase extends GeneratorBaseUseCase {
    private final FileRepository fileRepository;

    public GeneratorFileEditUseCase(GeneratorRepository generatorRepository, FileRepository fileRepository) {
        super(generatorRepository);
        this.fileRepository = fileRepository;
    }

    public GeneratorEntity editFile(String id, String oldFileId, String newFileId) {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();

        fileStructChangeFile(generatorEntity.getFileStructure().getDirectory(), oldFileId, newFileId);

        return generatorRepository.save(generatorEntity);
    }

    private void fileStructChangeFile(FileStructure.Directory dir, String oldId, String newId) {
        for (FileStructure.File file : dir.getFiles()) {
            if (file.getFileId().equals(oldId)) {
                file.setFileId(newId);
                FileEntity oldFile = fileRepository.findById(oldId).get();
                fileRepository.delete(oldFile);
                return;
            }
        }

        for (FileStructure.Directory childDir : dir.getDirectories()) {
            fileStructChangeFile(childDir, oldId, newId);
        }
    }
}
