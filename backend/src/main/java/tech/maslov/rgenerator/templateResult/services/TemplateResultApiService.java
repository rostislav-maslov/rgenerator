package tech.maslov.rgenerator.templateResult.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.maslov.rgenerator.generator.models.GeneratorDoc;
import tech.maslov.rgenerator.generator.services.GeneratorService;
import tech.maslov.rgenerator.generator.services.ZipService;
import tech.maslov.rgenerator.templateResult.api.request.TemplateResultCreateRequest;
import tech.maslov.rgenerator.templateResult.api.response.TemplateResultResponse;
import tech.maslov.rgenerator.templateResult.models.TemplateResultDoc;

import java.io.IOException;

@Service
public class TemplateResultApiService {
    @Autowired
    private TemplateResultService templateResultService;
    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private ZipService zipService;

    public TemplateResultResponse transform(TemplateResultDoc doc){
        TemplateResultResponse response = new TemplateResultResponse();
        response.setGenerateId(doc.getGeneratorId().toString());
        response.setId(doc.getId().toString());
        response.setResultFileId(doc.getResultFileId().toString());
        response.setStatus(doc.getStatus());
        return response;
    }

    public TemplateResultResponse create(TemplateResultCreateRequest request) {
        GeneratorDoc generatorDoc = generatorService.findById(request.getGenerateId());
        TemplateResultDoc templateResultDoc = new TemplateResultDoc();
        templateResultDoc.setContent(request.getContent());
        templateResultDoc.setGeneratorId(request.getGenerateId());
        templateResultService.save(templateResultDoc);

        templateResultDoc.setStatus(TemplateResultDoc.Status.IN_PROGRESS);
        templateResultService.save(templateResultDoc);
        try {
            templateResultDoc = zipService.generate(generatorDoc, templateResultDoc);
            templateResultDoc.setStatus(TemplateResultDoc.Status.SUCCESS);
            templateResultService.save(templateResultDoc);
        } catch (IOException e) {
            e.printStackTrace();
            templateResultDoc.setStatus(TemplateResultDoc.Status.ERROR);
            templateResultService.save(templateResultDoc);
        }

        return transform(templateResultDoc);
    }
}
