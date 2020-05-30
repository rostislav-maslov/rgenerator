package tech.maslov.rgenerator.domain.generator.usecase.all;

import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.file.port.FileRepository;
import com.rcore.domain.file.port.FileStorage;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;
import com.rcore.domain.user.port.UserRepository;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.generator.dto.FileContentDTO;
import tech.maslov.rgenerator.domain.generator.dto.GeneratorWithOwnerDTO;
import tech.maslov.rgenerator.domain.generator.entity.FileStructure;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GeneratorViewUseCase extends GeneratorBaseUseCase {

    private final FileStorage fileStorage;
    private final FileRepository fileRepository;


    public GeneratorViewUseCase(GeneratorRepository generatorRepository, FileStorage fileStorage, AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, DeveloperRepository developerRepository, UserRepository userRepository, FileRepository fileRepository) {
        super(generatorRepository, authorizationDevByTokenUseCase, developerRepository, userRepository);
        this.fileStorage = fileStorage;
        this.fileRepository = fileRepository;
    }

    public GeneratorWithOwnerDTO findId(String id) throws AuthenticationException, AuthorizationException {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();
        this.checkViewAccess(generatorEntity);

        UserEntity userEntity = userRepository.findById(generatorEntity.getOwnerId()).get();
        return GeneratorWithOwnerDTO.of(generatorEntity, userEntity);
    }

    public List<GeneratorWithOwnerDTO> findAll() {
        return generatorRepository.find(new SearchRequest("", 1000l, 0l, null, null))
                .getItems()
                .stream()
                .map((item) -> {
                    UserEntity userEntity = userRepository.findById(item.getOwnerId()).get();
                    return GeneratorWithOwnerDTO.of(item, userEntity);
                })
                .collect(Collectors.toList());
    }

    public List<GeneratorWithOwnerDTO> findMyGenerators() throws AuthenticationException {
        DeveloperEntity developerEntity = this.currentDeveloper();
        return generatorRepository.findMyGenerators(developerEntity, new SearchRequest("", 1000l, 0l, null, null))
                .getItems()
                .stream()
                .map((item) -> {
                    UserEntity userEntity = userRepository.findById(item.getOwnerId()).get();
                    return GeneratorWithOwnerDTO.of(item, userEntity);
                })
                .collect(Collectors.toList());
    }

    private List<FileContentDTO> transformFiles(FileStructure.Directory dir, String parentPath) {
        List<FileContentDTO> response = new ArrayList<>();

        for (FileStructure.File file : dir.getFiles()) {
            FileContentDTO responseFile = new FileContentDTO();
            responseFile.setFileId(file.getFileId());
            responseFile.setPath(parentPath + "/" + dir.getName() + "/" + file.getName());
            responseFile.setName(file.getName());

            response.add(responseFile);
        }

        for (FileStructure.Directory childDir : dir.getDirectories()) {
            response.addAll(
                    transformFiles(childDir, parentPath + "/" + dir.getName())
            );
        }

        return response;
    }

    private FileContentDTO getFile(FileStructure.Directory dir, String fileId) {
        List<FileContentDTO> files = this.transformFiles(dir, "");
        for (FileContentDTO file : files) {
            if (file.getFileId().equals(fileId)) {
                return file;
            }
        }

        return null;
    }

    public FileContentDTO fileView(String id, String fileId) throws AuthenticationException, AuthorizationException {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();
        this.checkViewAccess(generatorEntity);

        FileContentDTO file = getFile(generatorEntity.getFileStructure().getDirectory(), fileId);
        if (file == null) return null;

        try {
            FileEntity fileEntity = fileRepository.findById(file.getFileId()).get();
            InputStream inputStream = fileStorage.getInputStream(fileEntity.getFilePath()).get();
            String text = "";
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                text = scanner.useDelimiter("\\A").next();
            }

            file.setContent(text);
            file.setType(FileContentDTO.FileType.TEXT);
        } catch (Exception e) {
            file.setContent("");
            file.setType(FileContentDTO.FileType.OTHER);
        }

        return file;
    }
}
