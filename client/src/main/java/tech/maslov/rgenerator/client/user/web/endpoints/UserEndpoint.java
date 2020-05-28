package tech.maslov.rgenerator.client.user.web.endpoints;

import com.rcore.adapter.domain.token.TokenAdapter;
import com.rcore.adapter.domain.token.mapper.AccessTokenMapper;
import com.rcore.adapter.domain.user.UserAdapter;
import com.rcore.adapter.domain.user.dto.UserDTO;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.usecase.AuthorizationByTokenUseCase;
import com.rcore.domain.user.entity.UserEntity;
import com.rcore.domain.user.port.UserRepository;
import com.rcore.restapi.web.api.response.SuccessApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.maslov.rgenerator.adapter.developer.DeveloperAdapter;
import tech.maslov.rgenerator.adapter.developer.dto.DeveloperDTO;
import tech.maslov.rgenerator.client.user.application.UserWebMapper;
import tech.maslov.rgenerator.client.user.web.api.MeResponse;
import tech.maslov.rgenerator.client.user.web.routes.UserApiRoutes;
import tech.maslov.rgenerator.domain.developer.config.DeveloperConfig;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.usecase.all.MeUseCase;

@Secured({})
@Api(tags = "User API")
@RequiredArgsConstructor
@RestController
public class UserEndpoint {

    private final UserRepository userRepository;
    private final UserWebMapper userWebMapper = new UserWebMapper();
    private final DeveloperAdapter developerAdapter;


    @ApiOperation("Get info about current user")
    @GetMapping(value = UserApiRoutes.ME, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<MeResponse> me() throws AuthenticationException {
        DeveloperDTO developerDTO = developerAdapter.view.me();
        UserEntity userEntity = userRepository.findById(developerDTO.getId()).get();

        return SuccessApiResponse.of(userWebMapper.map(developerDTO, userEntity));
    }

}
