package tech.maslov.rgenerator.generator.events;

import tech.maslov.rgenerator.generator.models.GeneratorDoc;

public interface IGeneratorEvent {
    public void preSave(GeneratorDoc doc);
    public void afterSave(GeneratorDoc doc);

    public Boolean preDelete(GeneratorDoc doc);
    public void afterDelete(GeneratorDoc doc);
}
