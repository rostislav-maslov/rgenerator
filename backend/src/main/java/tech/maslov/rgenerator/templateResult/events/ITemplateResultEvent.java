package tech.maslov.rgenerator.templateResult.events;

import tech.maslov.rgenerator.templateResult.models.TemplateResultDoc;

public interface ITemplateResultEvent {
    public void preSave(TemplateResultDoc doc);
    public void afterSave(TemplateResultDoc doc);

    public Boolean preDelete(TemplateResultDoc doc);
    public void afterDelete(TemplateResultDoc doc);
}
