package tech.maslov.rgenerator.domain.templateResult.usecase.all;

import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;


class TemplateResultBaseUseCase {

    protected final TemplateResultRepository templateResultRepository;

    public TemplateResultBaseUseCase(TemplateResultRepository templateResultRepository) {
        this.templateResultRepository = templateResultRepository;
    }

}
