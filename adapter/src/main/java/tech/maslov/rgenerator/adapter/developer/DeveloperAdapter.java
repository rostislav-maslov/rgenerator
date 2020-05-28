package tech.maslov.rgenerator.adapter.developer;

import tech.maslov.rgenerator.domain.developer.config.DeveloperConfig;

public class DeveloperAdapter {
    public final DeveloperAdminAdapter crud;
    public final DeveloperRestAdapter view;

    public DeveloperAdapter(DeveloperConfig config) {
        this.crud = new DeveloperAdminAdapter(config);
        this.view = new DeveloperRestAdapter(config);
    }
}