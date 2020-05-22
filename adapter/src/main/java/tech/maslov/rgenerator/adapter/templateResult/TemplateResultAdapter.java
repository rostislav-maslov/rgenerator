package tech.maslov.rgenerator.adapter.templateResult;

import tech.maslov.rgenerator.domain.templateResult.config.TemplateResultConfig;

public class TemplateResultAdapter {
    public final TemplateResultCRUDAdapter crud;
    public final TemplateResultViewAdapter view;
    public final TemplateResultZipAdapter zip;

    public TemplateResultAdapter(TemplateResultConfig config) {
        this.crud = new TemplateResultCRUDAdapter(config);
        this.view = new TemplateResultViewAdapter(config);
        this.zip = new TemplateResultZipAdapter(config);
    }
}