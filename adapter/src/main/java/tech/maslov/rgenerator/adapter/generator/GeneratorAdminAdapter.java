package tech.maslov.rgenerator.adapter.generator;

import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.base.port.SearchResult;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.adapter.generator.dto.GeneratorDTO;
import tech.maslov.rgenerator.adapter.generator.mapper.GeneratorMapper;
import tech.maslov.rgenerator.domain.generator.config.GeneratorConfig;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class GeneratorAdminAdapter {
    private final GeneratorConfig config;
    private final GeneratorMapper mapper = new GeneratorMapper();

    public Optional<GeneratorDTO> findById(String id) throws AuthenticationException, AuthorizationException {
        return config.secured.viewUseCase()
                .findById(id)
                .map(mapper::map);
    }

    public SearchResult<GeneratorDTO> find(SearchRequest request) throws AuthenticationException, AuthorizationException {
        SearchResult<GeneratorEntity> result = config.secured.viewUseCase()
                .search(request);
        return SearchResult.withItemsAndCount(
                mapper.mapAll(result.getItems()),
                result.getCount()
        );
    }
}
