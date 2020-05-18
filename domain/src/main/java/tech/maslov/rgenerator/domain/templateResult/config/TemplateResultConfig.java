package tech.maslov.rgenerator.domain.templateResult.config;

import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.domain.templateResult.port.TemplateResultIdGenerator;
import lombok.RequiredArgsConstructor;
import com.rcore.domain.token.exception.AuthorizationException;
import tech.maslov.rgenerator.domain.templateResult.usecase.secured.*;

public class TemplateResultConfig {

    @RequiredArgsConstructor
    public static class Secured {
        protected final TemplateResultRepository templateResultRepository;
        protected final TemplateResultIdGenerator idGenerator;
        protected final AuthorizationByTokenUseCase authorizationByTokenUseCase;

        public SecuredTemplateResultCreateUseCase createUseCase() {
            return new SecuredTemplateResultCreateUseCase(this.templateResultRepository, idGenerator, this.authorizationByTokenUseCase);
        }

        public SecuredTemplateResultDeleteUseCase deleteUseCase() {
            return new SecuredTemplateResultDeleteUseCase(this.templateResultRepository, this.authorizationByTokenUseCase);
        }

        public SecuredTemplateResultUpdateUseCase updateUseCase() {
            return new SecuredTemplateResultUpdateUseCase(this.templateResultRepository, this.authorizationByTokenUseCase);
        }

        public SecuredTemplateResultViewUseCase viewUseCase() {
            return new SecuredTemplateResultViewUseCase(this.templateResultRepository, this.authorizationByTokenUseCase);
        }
    }

    @RequiredArgsConstructor
    public static class All {
        protected final TemplateResultRepository templateResultRepository;
    }

    protected final TemplateResultRepository templateResultRepository;
    protected final TemplateResultIdGenerator idGenerator;
    protected final AuthorizationByTokenUseCase authorizationByTokenUseCase;

    public final Secured secured;
    public final All all;

    public TemplateResultConfig(TemplateResultRepository templateResultRepository, TemplateResultIdGenerator idGenerator, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        this.templateResultRepository = templateResultRepository;
        this.idGenerator = idGenerator;
        this.authorizationByTokenUseCase = authorizationByTokenUseCase;

        this.secured = new Secured(this.templateResultRepository, this.idGenerator, this.authorizationByTokenUseCase);
        this.all = new All(this.templateResultRepository);
    }

}