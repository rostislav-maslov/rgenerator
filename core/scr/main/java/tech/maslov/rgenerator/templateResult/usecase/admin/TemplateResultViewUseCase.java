package tech.maslov.rgenerator.templateResult.usecase.admin;

import tech.maslov.rgenerator.templateResult.entity.TemplateResultEntity;
import tech.maslov.rgenerator.templateResult.port.TemplateResultRepository;
import tech.maslov.rgenerator.templateResult.role.AdminTemplateResultViewAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.entity.UserEntity;
import com.rcore.domain.base.port.SearchResult;

public class TemplateResultViewUseCase extends TemplateResultAdminBaseUseCase {

    public TemplateResultViewUseCase(UserEntity actor, TemplateResultRepository templateResultRepository) throws AuthorizationException {
        super(actor, templateResultRepository, new AdminTemplateResultViewAccess());
    }

    public TemplateResultEntity findById(String id) {
        return templateResultRepository.findById(id);
    }

    public TemplateResultEntity search(String id) {
        return templateResultRepository.findById(id);
    }

    public SearchResult<TemplateResultEntity> find(Long size, Long skip) {
        return templateResultRepository.find(size, skip);
    }

}