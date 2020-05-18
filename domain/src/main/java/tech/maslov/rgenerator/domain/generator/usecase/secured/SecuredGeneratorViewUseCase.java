package tech.maslov.rgenerator.domain.generator.usecase.secured;

import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;
import tech.maslov.rgenerator.domain.generator.access.GeneratorViewAccess;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import com.rcore.domain.base.port.SearchRequest;
import com.rcore.domain.base.port.SearchResult;

import java.util.Optional;

public class SecuredGeneratorViewUseCase extends SecuredGeneratorBaseUseCase {

    public SecuredGeneratorViewUseCase(GeneratorRepository generatorRepository, AuthorizationByTokenUseCase authorizationByTokenUseCase) {
        super(generatorRepository, new GeneratorViewAccess(), authorizationByTokenUseCase);
    }

    public Optional<GeneratorEntity> findById(String id) throws AuthenticationException, AuthorizationException {
        checkAccess();
        return generatorRepository.findById(id);
    }

    public SearchResult<GeneratorEntity> search(SearchRequest request) throws AuthenticationException, AuthorizationException {
        checkAccess();
        return generatorRepository.find(request);
    }

}