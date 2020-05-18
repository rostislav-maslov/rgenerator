package tech.maslov.rgenerator.adapter.templateResult;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.adapter.templateResult.dto.TemplateResultDTO;
import tech.maslov.rgenerator.adapter.templateResult.mapper.TemplateResultMapper;
import tech.maslov.rgenerator.domain.templateResult.config.TemplateResultConfig;

@RequiredArgsConstructor
public class TemplateResultCRUDAdapter {
    private final TemplateResultConfig config;
    private final TemplateResultMapper mapper = new TemplateResultMapper();

    public TemplateResultDTO create(TemplateResultDTO templateResult) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.secured.createUseCase()
                .create(mapper.inverseMap(templateResult)));
    }

    public TemplateResultDTO update(TemplateResultDTO templateResult) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.secured.createUseCase()
                .create(mapper.inverseMap(templateResult)));
    }

    public Boolean delete(TemplateResultDTO templateResult) throws AuthenticationException, AuthorizationException {
        return config.secured.deleteUseCase()
                .delete(mapper.inverseMap(templateResult));
    }

    public Boolean deleteById(String id) throws AuthenticationException, AuthorizationException {
        return config.secured.deleteUseCase()
                .deleteById(id);
    }
}
