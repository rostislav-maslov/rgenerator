package tech.maslov.rgenerator.generator.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maslov.rgenerator.api.base.response.BaseApiResponse;
import tech.maslov.rgenerator.generator.api.request.GeneratorAddRequest;
import tech.maslov.rgenerator.generator.api.response.GeneratorResponse;
import tech.maslov.rgenerator.generator.routes.GeneratorApiRoutes;
import tech.maslov.rgenerator.generator.services.GeneratorApiService;


@RestController
public class GeneratorApiController {

    @Autowired private GeneratorApiService generateApiService;

    @RequestMapping(value = GeneratorApiRoutes.ROOT, method = RequestMethod.POST)
    public BaseApiResponse<GeneratorResponse> create(@RequestBody GeneratorAddRequest request) {
        return BaseApiResponse.of(generateApiService.create(request));
    }

    @RequestMapping(value = GeneratorApiRoutes.BY_ID, method = RequestMethod.GET)
    public BaseApiResponse<GeneratorResponse> create(@PathVariable ObjectId id) {
        return BaseApiResponse.of(generateApiService.findId(id));
    }



}
