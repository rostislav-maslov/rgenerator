package tech.maslov.rgenerator.templateResult.usecase.admin;

import tech.maslov.rgenerator.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.templateResult.port.TemplateResultIdGenerator;
import tech.maslov.rgenerator.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.templateResult.role.AdminTemplateResultCreateAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;


public class TemplateResultCreateUseCase  extends TemplateResultAdminBaseUseCase {
    private final TemplateResultIdGenerator idGenerator;

    public TemplateResultCreateUseCase(UserEntity actor, TemplateResultRepository templateResultRepository, TemplateResultIdGenerator idGenerator) throws AuthorizationException {
        super(actor, templateResultRepository, new AdminTemplateResultCreateAccess());
        this.idGenerator = idGenerator;
    }

    public TemplateResultEntity create(TemplateResultEntity templateResultEntity) {
        templateResultEntity.setId(idGenerator.generate());

        templateResultEntity = templateResultRepository.save(templateResultEntity);

        return templateResultEntity;
    }


}