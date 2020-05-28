package tech.maslov.rgenerator.adapter.developer;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import tech.maslov.rgenerator.adapter.developer.dto.DeveloperDTO;
import tech.maslov.rgenerator.adapter.developer.mapper.DeveloperMapper;
import tech.maslov.rgenerator.domain.developer.config.DeveloperConfig;

@RequiredArgsConstructor
public class DeveloperAdminAdapter {
    private final DeveloperConfig config;
    private final DeveloperMapper mapper = new DeveloperMapper();

    public DeveloperDTO create(DeveloperDTO developer) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.secured.createUseCase()
                .create(mapper.inverseMap(developer)));
    }

    public DeveloperDTO update(DeveloperDTO developer) throws AuthenticationException, AuthorizationException {
        return mapper.map(config.secured.createUseCase()
                .create(mapper.inverseMap(developer)));
    }

    public Boolean delete(DeveloperDTO developer) throws AuthenticationException, AuthorizationException {
        return config.secured.deleteUseCase()
                .delete(mapper.inverseMap(developer));
    }

//    public Boolean deleteById(String id) throws AuthenticationException, AuthorizationException {
//        return config.secured.deleteUseCase()
//                .deleteById(id);
//    }
}
