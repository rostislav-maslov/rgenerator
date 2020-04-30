package tech.maslov.rgenerator.templateResult.config;

import tech.maslov.rgenerator.templateResult.port.TemplateResultIdGenerator;
import tech.maslov.rgenerator.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.templateResult.usecase.admin.TemplateResultCreateUseCase;
import tech.maslov.rgenerator.templateResult.usecase.admin.TemplateResultDeleteUseCase;
import tech.maslov.rgenerator.templateResult.usecase.admin.TemplateResultUpdateUseCase;
import tech.maslov.rgenerator.templateResult.usecase.admin.TemplateResultViewUseCase;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;

public class TemplateResultConfig {

    public static class Admin {
        protected final TemplateResultRepository templateResultRepository;
        protected final TemplateResultIdGenerator idGenerator;

        public Admin(TemplateResultRepository templateResultRepository, TemplateResultIdGenerator idGenerator) {
            this.templateResultRepository = templateResultRepository;
            this.idGenerator = idGenerator;
        }

        public TemplateResultCreateUseCase createUseCase(UserEntity actor) throws AuthorizationException {
            return new TemplateResultCreateUseCase(actor, this.templateResultRepository, idGenerator);
        }

        public TemplateResultDeleteUseCase deleteUseCase(UserEntity actor) throws AuthorizationException {
            return new TemplateResultDeleteUseCase(actor, this.templateResultRepository);
        }

        public TemplateResultUpdateUseCase updateUseCase(UserEntity actor) throws AuthorizationException {
            return new TemplateResultUpdateUseCase(actor, this.templateResultRepository);
        }

        public TemplateResultViewUseCase viewUseCase(UserEntity actor) throws AuthorizationException {
            return new TemplateResultViewUseCase(actor, this.templateResultRepository);
        }
    }

    public static class All {

        public All() {
        }

    }

    private final TemplateResultRepository templateResultRepository;
    private final TemplateResultIdGenerator idGenerator;

    public final Admin admin;
    public final All all;

    public TemplateResultConfig(
            TemplateResultRepository templateResultRepository,
            TemplateResultIdGenerator idGenerator
    ) {
        this.templateResultRepository = templateResultRepository;
        this.idGenerator = idGenerator;

        this.admin = new Admin(this.templateResultRepository, this.idGenerator);
        this.all = new All();
    }

}
