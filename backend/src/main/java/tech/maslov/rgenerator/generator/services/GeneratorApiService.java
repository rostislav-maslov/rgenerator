package tech.maslov.rgenerator.generator.services;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.ub.core.file.services.FileService;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.maslov.rgenerator.generator.api.request.*;
import tech.maslov.rgenerator.generator.api.response.GeneratorResponse;
import tech.maslov.rgenerator.generator.models.FileStructure;
import tech.maslov.rgenerator.generator.models.GeneratorDoc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class GeneratorApiService {

    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private FileService fileService;

    private List<GeneratorResponse.File> transformFiles(FileStructure.Directory dir, String parentPath) {
        List<GeneratorResponse.File> response = new ArrayList<>();

        for (FileStructure.File file : dir.getFiles()) {
            GeneratorResponse.File responseFile = new GeneratorResponse.File();
            responseFile.setFileId(file.getFileId().toString());
            responseFile.setPath(parentPath + "/" + dir.getName() + "/" + file.getName());

            response.add(responseFile);
        }

        for (FileStructure.Directory childDir : dir.getDirectories()) {
            response.addAll(
                    transformFiles(childDir, parentPath + "/" + dir.getName())
            );
        }

        return response;
    }

    private GeneratorResponse transform(GeneratorDoc doc) {
        GeneratorResponse response = new GeneratorResponse();

        response.setTitle(doc.getTitle());
        response.setDescription(doc.getDescription());
        response.setExample(doc.getExample());
        response.setId(doc.getId().toString());
        response.setFiles(transformFiles(doc.getFileStructure().getDirectory(), ""));

        return response;
    }




















}
