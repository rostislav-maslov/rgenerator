package tech.maslov.rgenerator.templateResult.usecase.client;

import tech.maslov.rgenerator.templateResult.port.TemplateResultRepository;

class TemplateResultBaseUseCase{

    protected final TemplateResultRepository templateResultRepository;

    public TemplateResultBaseUseCase(TemplateResultRepository templateResultRepository) {
        this.templateResultRepository = templateResultRepository;
    }

}
