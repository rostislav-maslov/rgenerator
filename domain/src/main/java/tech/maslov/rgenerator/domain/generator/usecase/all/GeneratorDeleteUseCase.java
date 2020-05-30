package tech.maslov.rgenerator.domain.generator.usecase.all;

import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.file.port.FileRepository;
import com.rcore.domain.file.port.FileStorage;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.port.UserRepository;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.generator.entity.FileStructure;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.templateResult.usecase.all.TemplateResultDeleteUseCase;

import java.util.Optional;

public class GeneratorDeleteUseCase extends GeneratorBaseUseCase {

    private final FileStorage fileStorage;
    private final FileRepository fileRepository;
    private final TemplateResultDeleteUseCase templateResultDeleteUseCase;


    public GeneratorDeleteUseCase(GeneratorRepository generatorRepository, FileStorage fileStorage, AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, DeveloperRepository developerRepository, UserRepository userRepository, FileRepository fileRepository, TemplateResultDeleteUseCase templateResultDeleteUseCase) {
        super(generatorRepository, authorizationDevByTokenUseCase, developerRepository, userRepository);
        this.fileStorage = fileStorage;
        this.fileRepository = fileRepository;
        this.templateResultDeleteUseCase = templateResultDeleteUseCase;
    }

    public static void deleteFilesInDirectory(FileStructure.Directory directory, FileStorage fileStorage, FileRepository fileRepository){
        for(FileStructure.File file : directory.getFiles()){
            Optional<FileEntity> fileEntityOpt = fileRepository.findById(file.getFileId());
            if(fileEntityOpt.isEmpty()) continue;
            FileEntity fileEntity = fileEntityOpt.get();

            fileStorage.remove(fileEntity.getFilePath());
            fileRepository.deleteById(fileEntity.getId());
        }

        for(FileStructure.Directory child: directory.getDirectories()){
            deleteFilesInDirectory(child, fileStorage, fileRepository);
        }
    }

    public void delete(String id) throws AuthenticationException, AuthorizationException {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();
        DeveloperEntity developerEntity = currentDeveloper();

        if(developerEntity.getId().equals(generatorEntity.getOwnerId()) == false) throw new AuthorizationException();

        // Удаляем все файлы
        deleteFilesInDirectory(generatorEntity.getFileStructure().getDirectory(), fileStorage, fileRepository);

        // Удаляем все темплейты
        templateResultDeleteUseCase.deleteByGenerator(generatorEntity);

        // Удаляем генератор
        generatorRepository.deleteById(generatorEntity.getId());

        // Generator RIP
    }


}
