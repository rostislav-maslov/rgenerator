package tech.maslov.rgenerator.adapter.templateResult;

import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.base.port.SearchResult;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.adapter.templateResult.dto.TemplateResultDTO;
import tech.maslov.rgenerator.adapter.templateResult.mapper.TemplateResultMapper;
import tech.maslov.rgenerator.domain.templateResult.config.TemplateResultConfig;
import tech.maslov.rgenerator.domain.templateResult.entity.TemplateResultEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class TemplateResultViewAdapter {
    private final TemplateResultConfig config;
    private final TemplateResultMapper mapper = new TemplateResultMapper();

    public Optional<TemplateResultDTO> findById(String id) throws AuthenticationException, AuthorizationException {
        return config.secured.viewUseCase()
                .findById(id)
                .map(mapper::map);
    }

    public SearchResult<TemplateResultDTO> find(SearchRequest request) throws AuthenticationException, AuthorizationException {
        SearchResult<TemplateResultEntity> result = config.secured.viewUseCase()
                .search(request);
        return SearchResult.withItemsAndCount(
                mapper.mapAll(result.getItems()),
                result.getCount()
        );
    }
}
