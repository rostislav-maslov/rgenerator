package tech.maslov.rgenerator.generator.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maslov.rgenerator.api.base.response.BaseApiResponse;
import tech.maslov.rgenerator.api.base.response.ListApiResponse;
import tech.maslov.rgenerator.generator.api.request.*;
import tech.maslov.rgenerator.generator.api.response.GeneratorResponse;
import tech.maslov.rgenerator.generator.routes.GeneratorApiRoutes;
import tech.maslov.rgenerator.generator.services.GeneratorApiService;

import java.util.List;


@RestController
public class GeneratorApiController {

    @Autowired
    private GeneratorApiService generateApiService;

    @RequestMapping(value = GeneratorApiRoutes.ROOT, method = RequestMethod.POST)
    public BaseApiResponse<GeneratorResponse> create(@RequestBody GeneratorAddRequest request) {
        return BaseApiResponse.of(generateApiService.create(request));
    }

    @RequestMapping(value = GeneratorApiRoutes.BY_ID, method = RequestMethod.GET)
    public BaseApiResponse<GeneratorResponse> create(@PathVariable ObjectId id) {
        return BaseApiResponse.of(generateApiService.findId(id));
    }

    @RequestMapping(value = GeneratorApiRoutes.INFO, method = RequestMethod.POST)
    public BaseApiResponse<GeneratorResponse> editInfo(@PathVariable ObjectId id, @RequestBody GeneratorInfoRequest request) {
        return BaseApiResponse.of(generateApiService.editInfo(id, request));
    }

    @RequestMapping(value = GeneratorApiRoutes.JSON, method = RequestMethod.POST)
    public BaseApiResponse<GeneratorResponse> editJson(@PathVariable ObjectId id, @RequestBody GeneratorJsonRequest request) {
        return BaseApiResponse.of(generateApiService.editJson(id, request));
    }

    @RequestMapping(value = GeneratorApiRoutes.FILE, method = RequestMethod.POST)
    public BaseApiResponse<GeneratorResponse> editFile(@PathVariable ObjectId id, @RequestBody GeneratorFileEditRequest request) {
        return BaseApiResponse.of(generateApiService.editFile(id, request));
    }

    @RequestMapping(value = GeneratorApiRoutes.FILE, method = RequestMethod.DELETE)
    public BaseApiResponse<GeneratorResponse> deleteFile(@PathVariable ObjectId id, @PathVariable ObjectId fileId) {
        return BaseApiResponse.of(generateApiService.removeFile(id, fileId));
    }

    @RequestMapping(value = GeneratorApiRoutes.ROOT, method = RequestMethod.GET)
    public ListApiResponse<GeneratorResponse> list() {
        List<GeneratorResponse> list = generateApiService.findAll();

        return ListApiResponse.of(list, (long)(list.size()));
    }



}
