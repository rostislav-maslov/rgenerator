package tech.maslov.rgenerator.domain.generator.usecase.all;

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

    private void deleteFilesInDirectory(FileStructure.Directory directory){
        for(FileStructure.File file : directory.getFiles()){
            fileStorage.remove(file.getFileId());
            fileRepository.deleteById(file.getFileId());
        }

        for(FileStructure.Directory child: directory.getDirectories()){
            deleteFilesInDirectory(child);
        }
    }

    public void delete(String id) throws AuthenticationException, AuthorizationException {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();
        DeveloperEntity developerEntity = currentDeveloper();

        if(developerEntity.getId().equals(generatorEntity.getOwnerId()) == false) throw new AuthorizationException();

        // Удаляем все файлы
        deleteFilesInDirectory(generatorEntity.getFileStructure().getDirectory());

        // Удаляем все темплейты
        templateResultDeleteUseCase.deleteByGenerator(generatorEntity);

        // Удаляем генератор
        generatorRepository.deleteById(generatorEntity.getId());

        // Generator RIP
    }


}
