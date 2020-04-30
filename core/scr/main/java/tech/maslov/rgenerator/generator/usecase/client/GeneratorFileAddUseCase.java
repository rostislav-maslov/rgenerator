package tech.maslov.rgenerator.generator.usecase.client;

import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.file.port.FileRepository;
import tech.maslov.rgenerator.generator.entity.FileStructure;
import tech.maslov.rgenerator.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.generator.port.GeneratorRepository;

public class GeneratorFileAddUseCase extends GeneratorBaseUseCase {
    private final FileRepository fileRepository;

    public GeneratorFileAddUseCase(GeneratorRepository generatorRepository, FileRepository fileRepository) {
        super(generatorRepository);
        this.fileRepository = fileRepository;
    }

    public FileStructure.File fileAdd(String id, String path, FileEntity fileEntity) {
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
        FileStructure.File response = new FileStructure.File();

        if (result != null) {
            response.setFileId(result.getFileId());
        }

        return response;
    }
}
