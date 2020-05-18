package tech.maslov.rgenerator.adapter.generator;

import tech.maslov.rgenerator.domain.generator.config.GeneratorConfig;

public class GeneratorAdapter {
    public final GeneratorCRUDAdapter crud;
    public final GeneratorViewAdapter view;

    public GeneratorAdapter(GeneratorConfig config) {
        this.crud = new GeneratorCRUDAdapter(config);
        this.view = new GeneratorViewAdapter(config);
    }
}