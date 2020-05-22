package tech.maslov.rgenerator.domain.generator.usecase.all;

import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.file.port.FileRepository;
import tech.maslov.rgenerator.domain.generator.dto.FileContentDTO;
import tech.maslov.rgenerator.domain.generator.entity.FileStructure;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;

import java.util.ArrayList;
import java.util.List;

public class GeneratorEditUseCase extends GeneratorBaseUseCase {
    private final FileRepository fileRepository;

    public GeneratorEditUseCase(GeneratorRepository generatorRepository, FileRepository fileRepository) {
        super(generatorRepository);
        this.fileRepository = fileRepository;
    }

    public GeneratorEntity editInfo(String id, String title, String description) {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();

        generatorEntity.setTitle(title);
        generatorEntity.setDescription(description);

        generatorRepository.save(generatorEntity);

        return generatorEntity;
    }

    public GeneratorEntity editJson(String id, String example) {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();

        generatorEntity.setExample(example);

        return generatorRepository.save(generatorEntity);
    }

    private void fileStructChangeFile(FileStructure.Directory dir, String oldId, String newId) {
        for (FileStructure.File file : dir.getFiles()) {
            if (file.getFileId().equals(oldId)) {
                file.setFileId(newId);
                fileRepository.deleteById(oldId);
                return;
            }
        }

        for (FileStructure.Directory childDir : dir.getDirectories()) {
            fileStructChangeFile(childDir, oldId, newId);
        }
    }

    private void fileStructDeleteFile(FileStructure.Directory dir, String oldId) {
        List<FileStructure.File> toDelete = new ArrayList<>();

        for (FileStructure.File file : dir.getFiles()) {
            if (file.getFileId().equals(oldId)) {
                fileRepository.deleteById(oldId);
                toDelete.add(file);
            }
        }
        dir.getFiles().removeAll(toDelete);

        for (FileStructure.Directory childDir : dir.getDirectories()) {
            fileStructDeleteFile(childDir, oldId);
        }
    }

    public GeneratorEntity editFile(String id, String oldFileId, String newFileId) {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();

        fileStructChangeFile(generatorEntity.getFileStructure().getDirectory(), oldFileId, newFileId);

        return generatorRepository.save(generatorEntity);
    }

    public GeneratorEntity removeFile(String id, String fileId) {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();

        fileStructDeleteFile(generatorEntity.getFileStructure().getDirectory(), fileId);

        return generatorRepository.save(generatorEntity);
    }

    public FileContentDTO fileAdd(String id, String path, FileEntity fileEntity) {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();

        String[] pathArr = path.split("/");
        Integer idx = 0;

        FileStructure.Directory dir = null;
        FileStructure.File result = null;

        if (generatorEntity.getFileStructure().getDirectory().getName() == null) {
            generatorEntity.getFileStructure().getDirectory().setName("root");
        }

        dir = generatorEntity.getFileStructure().getDirectory();

        for (String name : pathArr) {
            if (name.equals("")) {
                idx = idx + 1;
                continue;
            }

            if (name.equals("root") && pathArr[0].equals("") && idx == 1) {
                idx = 2;
                continue;
            }

            if (idx == pathArr.length - 1) {
                //file
                if (dir == null) {
                    dir = generatorEntity.getFileStructure().getDirectory();
                    if (dir.getName() == null) {
                        dir.setName("root");
                    }
                }

                FileStructure.File toChange = null;
                for (FileStructure.File fileObj : dir.getFiles()) {
                    if (fileObj.getName().equals(name)) {
                        //TODO: уже существует. Пока не знаю что с этим делать
                        toChange = fileObj;
                        result = toChange;
                    }
                }

                if (toChange == null) {
                    toChange = new FileStructure.File();
                    toChange.setName(name);
                    toChange.setFileId(fileEntity.getId());
                    dir.getFiles().add(toChange);

                    result = toChange;
                }
            } else {
                //dir
                if (dir == null) {
                    dir = generatorEntity.getFileStructure().getDirectory();
                    if (dir.getName() == null) {
                        dir.setName(name);
                    }
                } else {
                    FileStructure.Directory child = null;
                    for (FileStructure.Directory childDir : dir.getDirectories()) {
                        if (childDir.getName().equals(name)) {
                            child = childDir;
                        }
                    }

                    if (child == null) {
                        child = new FileStructure.Directory();
                        child.setName(name);
                        dir.getDirectories().add(child);
                    }
                    dir = child;
                }
            }
            idx = idx + 1;
        }
        generatorRepository.save(generatorEntity);

        FileContentDTO fileContentDTO = new FileContentDTO();
        fileContentDTO.setName(result.getName());
        fileContentDTO.setFileId(result.getFileId());

        return fileContentDTO;
    }
}
