package tech.maslov.rgenerator.adapter.generator;

import tech.maslov.rgenerator.domain.generator.config.GeneratorConfig;

public class GeneratorAdapter {
    public final GeneratorClientAdapter client;
    public final GeneratorAdminAdapter admin;

    public GeneratorAdapter(GeneratorConfig config) {
        this.client = new GeneratorClientAdapter(config);
        this.admin = new GeneratorAdminAdapter(config);
    }
}