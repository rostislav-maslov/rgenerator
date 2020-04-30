package tech.maslov.rgenerator.templateResult.usecase.admin;

import tech.maslov.rgenerator.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.templateResult.role.AdminTemplateResultDeleteAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;

public class TemplateResultDeleteUseCase  extends TemplateResultAdminBaseUseCase {

    public TemplateResultDeleteUseCase(UserEntity actor, TemplateResultRepository templateResultRepository) throws AuthorizationException {
        super(actor, templateResultRepository, new AdminTemplateResultDeleteAccess());
    }

    public Boolean delete(TemplateResultEntity templateResultEntity){
        templateResultRepository.delete(templateResultEntity);

        return true;
    }


}