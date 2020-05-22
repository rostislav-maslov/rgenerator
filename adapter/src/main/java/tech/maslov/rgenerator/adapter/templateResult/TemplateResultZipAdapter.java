package tech.maslov.rgenerator.adapter.templateResult;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.adapter.templateResult.dto.TemplateResultDTO;
import tech.maslov.rgenerator.adapter.templateResult.mapper.TemplateResultMapper;
import tech.maslov.rgenerator.domain.templateResult.config.TemplateResultConfig;

@RequiredArgsConstructor
public class TemplateResultZipAdapter {
    private final TemplateResultConfig config;
    private final TemplateResultMapper mapper = new TemplateResultMapper();

    public TemplateResultDTO zip(String generateId, String content) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.all.createUseCase().create(generateId, content));
    }
}
