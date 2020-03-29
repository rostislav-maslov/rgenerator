package tech.maslov.rgenerator.generator.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.maslov.rgenerator.api.base.response.BaseApiResponse;
import tech.maslov.rgenerator.generator.api.response.GeneratorResponse;
import tech.maslov.rgenerator.generator.routes.GeneratorApiRoutes;
import tech.maslov.rgenerator.generator.services.GeneratorApiService;

import java.io.IOException;

@Controller
public class GeneratorUploadController {

    @Autowired private GeneratorApiService generateApiService;

    @ResponseBody
    @RequestMapping(value = GeneratorApiRoutes.FILE_UPLOAD, method = RequestMethod.POST)
    public BaseApiResponse<GeneratorResponse> create(@RequestParam ObjectId id, @RequestParam String path, @RequestParam MultipartFile file) throws IOException {
        return BaseApiResponse.of(generateApiService.fileAdd(id, path, file));
    }

}
