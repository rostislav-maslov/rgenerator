package tech.maslov.rgenerator.domain.generator.usecase.all;

import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.file.port.FileStorage;
import tech.maslov.rgenerator.domain.generator.dto.FileContentDTO;
import tech.maslov.rgenerator.domain.generator.entity.FileStructure;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class GeneratorViewUseCase extends GeneratorBaseUseCase {

    private final FileStorage fileStorage;

    public GeneratorViewUseCase(GeneratorRepository generatorRepository, FileStorage fileStorage) {
        super(generatorRepository);
        this.fileStorage = fileStorage;
    }

    public GeneratorEntity findId(String id) {
        return generatorRepository.findById(id).get();
    }

    public List<GeneratorEntity> findAll() {
        return generatorRepository.find(new SearchRequest("", 1000l, 0l, null, null)).getItems();
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

    public FileContentDTO fileView(String id, String fileId) {
        GeneratorEntity generatorEntity = generatorRepository.findById(id).get();
        FileContentDTO file = getFile(generatorEntity.getFileStructure().getDirectory(), fileId);
        if (file == null) return null;

        try {
            InputStream inputStream = fileStorage.getInputStream(fileId).get();
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
