package tech.maslov.rgenerator.domain.developer.usecase.all;

import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;


class DeveloperBaseUseCase {

    protected final DeveloperRepository developerRepository;

    public DeveloperBaseUseCase(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

}
