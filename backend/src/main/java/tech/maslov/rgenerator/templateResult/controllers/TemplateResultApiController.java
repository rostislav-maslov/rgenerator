package tech.maslov.rgenerator.templateResult.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.maslov.rgenerator.api.base.response.BaseApiResponse;
import tech.maslov.rgenerator.templateResult.api.request.TemplateResultCreateRequest;
import tech.maslov.rgenerator.templateResult.api.response.TemplateResultResponse;
import tech.maslov.rgenerator.templateResult.routes.TemplateResultApiRouter;
import tech.maslov.rgenerator.templateResult.services.TemplateResultApiService;

import java.io.IOException;


@RestController
public class TemplateResultApiController {

    @Autowired private TemplateResultApiService templateResultApiService;

    @RequestMapping(value = TemplateResultApiRouter.ROOT, method = RequestMethod.POST)
    public BaseApiResponse<TemplateResultResponse> create(@RequestBody TemplateResultCreateRequest request) throws IOException {
        return BaseApiResponse.of(templateResultApiService.create(request));
    }

}
