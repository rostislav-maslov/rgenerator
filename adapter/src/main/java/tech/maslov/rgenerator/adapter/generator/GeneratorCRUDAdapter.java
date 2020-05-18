package tech.maslov.rgenerator.adapter.generator;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.adapter.generator.dto.GeneratorDTO;
import tech.maslov.rgenerator.adapter.generator.mapper.GeneratorMapper;
import tech.maslov.rgenerator.domain.generator.config.GeneratorConfig;

@RequiredArgsConstructor
public class GeneratorCRUDAdapter {
    private final GeneratorConfig config;
    private final GeneratorMapper mapper = new GeneratorMapper();

    public GeneratorDTO create(GeneratorDTO generator) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.secured.createUseCase()
                .create(mapper.inverseMap(generator)));
    }

    public GeneratorDTO update(GeneratorDTO generator) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.secured.createUseCase()
                .create(mapper.inverseMap(generator)));
    }

    public Boolean delete(GeneratorDTO generator) throws AuthenticationException, AuthorizationException {
        return config.secured.deleteUseCase()
                .delete(mapper.inverseMap(generator));
    }

    public Boolean deleteById(String id) throws AuthenticationException, AuthorizationException {
        return config.secured.deleteUseCase()
                .deleteById(id);
    }
}
