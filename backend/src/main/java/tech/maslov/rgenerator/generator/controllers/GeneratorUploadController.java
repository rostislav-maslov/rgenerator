package tech.maslov.rgenerator.generator.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.maslov.rgenerator.api.base.response.BaseApiResponse;
import tech.maslov.rgenerator.generator.api.response.GeneratorResponse;
import tech.maslov.rgenerator.generator.models.GeneratorDoc;
import tech.maslov.rgenerator.generator.routes.GeneratorApiRoutes;
import tech.maslov.rgenerator.generator.services.GeneratorApiService;
import tech.maslov.rgenerator.generator.services.GeneratorService;
import tech.maslov.rgenerator.generator.services.ZipService;

import java.io.IOException;
import java.util.Map;

@Controller
public class GeneratorUploadController {

    @Autowired private GeneratorApiService generateApiService;
    @Autowired private GeneratorService generatorService;
    @Autowired private ZipService zipService;

    @ResponseBody
    @RequestMapping(value = GeneratorApiRoutes.FILE_UPLOAD, method = RequestMethod.POST)
    public BaseApiResponse<GeneratorResponse.File> create(@RequestParam ObjectId id, @RequestParam String path, @RequestParam MultipartFile file) throws IOException {
        return BaseApiResponse.of(generateApiService.fileAdd(id, path, file));
    }

    @ResponseBody
    @RequestMapping(value = GeneratorApiRoutes.GENERATE, method = RequestMethod.POST, produces = "text/plain")
    public String create(@PathVariable ObjectId id, @RequestParam String content) throws IOException {
        GeneratorDoc generatorDoc = generatorService.findById(id);
        Map data = zipService.contentObject(generatorDoc.getExample());
        String result = zipService.genTemplate(data, content);
        return result;
    }

}
