package tech.maslov.rgenerator.templateResult.usecase.admin;

import tech.maslov.rgenerator.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.templateResult.role.AdminTemplateResultUpdateAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;

public class TemplateResultUpdateUseCase  extends TemplateResultAdminBaseUseCase {

    public TemplateResultUpdateUseCase(UserEntity actor, TemplateResultRepository templateResultRepository)throws AuthorizationException {
        super(actor, templateResultRepository, new AdminTemplateResultUpdateAccess());
    }

    public TemplateResultEntity update(TemplateResultEntity templateResultEntity){
        templateResultEntity.setUpdatedAt(new Date());
        return templateResultRepository.save(templateResultEntity);
    }


}