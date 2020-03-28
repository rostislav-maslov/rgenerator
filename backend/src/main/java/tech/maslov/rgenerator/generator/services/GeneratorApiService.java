package tech.maslov.rgenerator.generator.services;

import com.mongodb.gridfs.GridFSFile;
import com.ub.core.file.services.FileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tech.maslov.rgenerator.generator.api.request.GeneratorAddRequest;
import tech.maslov.rgenerator.generator.api.response.GeneratorResponse;
import tech.maslov.rgenerator.generator.models.FileStructure;
import tech.maslov.rgenerator.generator.models.GeneratorDoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneratorApiService {

    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private FileService fileService;

    private GeneratorResponse transform(GeneratorDoc doc) {
        GeneratorResponse response = new GeneratorResponse();

        response.setTitle(doc.getTitle());
        response.setDescription(doc.getDescription());
        response.setExample(doc.getExample());
        response.setId(doc.getId().toString());

        return response;
    }

    public GeneratorResponse create(GeneratorAddRequest request) {
        GeneratorDoc generatorDoc = new GeneratorDoc();

        generatorDoc.setTitle(request.getTitle());
        generatorDoc.setDescription(request.getDescription());
        generatorDoc.setExample(request.getExample());

        return transform(generatorService.save(generatorDoc));
    }

    public GeneratorResponse findId(ObjectId id) {
        GeneratorDoc generatorDoc = generatorService.findById(id);
        return transform(generatorService.save(generatorDoc));
    }

    public List<GeneratorResponse> findAll() {
        List<GeneratorDoc> generatorDocs = generatorService.findAll();
        List<GeneratorResponse> responses = new ArrayList<>();
        for(GeneratorDoc doc: generatorDocs){
            responses.add(transform(doc));
        }

        return responses;
    }

    public GeneratorResponse fileAdd(ObjectId id, String path, MultipartFile file) {
        GeneratorDoc generatorDoc = generatorService.findById(id);

        String[] pathArr = path.split("/");
        Integer idx = 0;

        FileStructure.Directory dir = null;

        for (String name : pathArr) {
            if(idx == pathArr.length -1){
                //file
                if(dir == null){
                    dir = generatorDoc.getFileStructure().getDirectory();
                    if(dir.getName() == null){
                        dir.setName("root");
                    }
                }

                FileStructure.File toChange = null;
                for(FileStructure.File fileObj: dir.getFiles()){
                    if(fileObj.getName().equals(name)){
                        //TODO: уже существует. Пока не знаю что с этим делать
                        toChange = fileObj;
                    }
                }

                if(toChange == null){
                    toChange = new FileStructure.File();
                    toChange.setName(name);
                    GridFSFile grid = fileService.save(file);
                    toChange.setFileId((ObjectId) grid.getId());
                    dir.getFiles().add(toChange);
                }
            }else{
                //dir
                if(dir == null){
                    dir = generatorDoc.getFileStructure().getDirectory();
                    if(dir.getName() == null){
                        dir.setName(name);
                    }
                }else{
                    FileStructure.Directory child = null;
                    for(FileStructure.Directory childDir : dir.getDirectories()){
                        if(childDir.getName().equals(name)){
                            child = childDir;
                        }
                    }

                    if(child == null) {
                        child = new FileStructure.Directory();
                        child.setName(name);
                        dir.getDirectories().add(child);
                    }
                    dir = child;
                }
            }
            idx = idx + 1;
        }

        return transform(generatorService.save(generatorDoc));
    }
}
