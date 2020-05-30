package tech.maslov.rgenerator.client.user.web.endpoints;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.user.entity.UserEntity;
import com.rcore.domain.user.port.UserRepository;
import com.rcore.restapi.web.api.response.SuccessApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tech.maslov.rgenerator.adapter.developer.DeveloperAdapter;
import tech.maslov.rgenerator.adapter.developer.dto.DeveloperDTO;
import tech.maslov.rgenerator.client.user.application.UserWebMapper;
import tech.maslov.rgenerator.client.user.web.api.request.GithubRequest;
import tech.maslov.rgenerator.client.user.web.api.response.MeResponse;
import tech.maslov.rgenerator.client.user.web.routes.UserApiRoutes;

import java.io.IOException;

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

    @ApiOperation("Auth user by GitHub Account")
    @PostMapping(value = UserApiRoutes.GITHUB, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<String> github(@RequestBody GithubRequest request) throws AuthenticationException, IOException {
        developerAdapter.view.githubSignIn(request.getCode());

        return SuccessApiResponse.of(HttpStatus.OK.toString());
    }

}
