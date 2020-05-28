package tech.maslov.rgenerator.client.auth.web.endpoints;

import com.rcore.adapter.domain.token.TokenAdapter;
import com.rcore.adapter.domain.token.dto.AccessTokenDTO;
import com.rcore.adapter.domain.token.dto.RefreshTokenDTO;
import com.rcore.adapter.domain.user.UserAdapter;
import com.rcore.adapter.domain.user.dto.TokenPairDTO;
import com.rcore.adapter.domain.user.dto.UserDTO;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.RefreshTokenCreationException;
import com.rcore.domain.user.exception.UserAlreadyExistException;
import com.rcore.domain.user.exception.UserBlockedException;
import com.rcore.domain.user.exception.UserNotFoundException;
import com.rcore.restapi.routes.BaseAuthRoutes;
import com.rcore.restapi.security.web.api.AuthenticationDTO;
import com.rcore.restapi.security.web.api.RefreshTokenRequest;
import com.rcore.restapi.security.web.api.UserCredentialsDTO;
import com.rcore.restapi.web.api.response.OkApiResponse;
import com.rcore.restapi.web.api.response.SuccessApiResponse;
import com.rcore.security.infrastructure.AuthTokenGenerator;
import com.rcore.security.infrastructure.exceptions.InvalidTokenFormatException;
import com.rcore.security.infrastructure.exceptions.TokenGenerateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.maslov.rgenerator.adapter.developer.DeveloperAdapter;
import tech.maslov.rgenerator.client.auth.web.api.request.ForgotPasswordChangeRequest;
import tech.maslov.rgenerator.client.auth.web.api.request.ForgotPasswordRequest;
import tech.maslov.rgenerator.client.auth.web.api.request.LoginRequest;
import tech.maslov.rgenerator.client.auth.web.api.request.SignUpRequest;
import tech.maslov.rgenerator.client.auth.web.routes.AuthApiRoutes;

@Secured({})
@Api(tags = "Auth API")
@RequiredArgsConstructor
@RestController
public class AuthEndpoint {

    @Value("${rcore.security.jwt.key}")
    private String secretKey;

    private final UserAdapter userAdapter;
    private final DeveloperAdapter developerAdapter;
    private final TokenAdapter tokenAdapter;
    private final AuthTokenGenerator<AccessTokenDTO> accessTokenGenerator;
    private final AuthTokenGenerator<RefreshTokenDTO> refreshTokenGenerator;



    @ApiOperation("SignUp")
    @PostMapping(value = AuthApiRoutes.SIGN_UP, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<AuthenticationDTO> signUp(@RequestBody SignUpRequest signUpRequest) throws RefreshTokenCreationException, UserAlreadyExistException, AuthenticationException, UserNotFoundException, UserBlockedException, TokenGenerateException {
        TokenPairDTO tokenPair = developerAdapter.view.signUp(signUpRequest.getLogin(), signUpRequest.getEmail(), signUpRequest.getPassword());

        return SuccessApiResponse.of(AuthenticationDTO.builder()
                .accessToken(accessTokenGenerator.generate(tokenPair.getAccessToken(), secretKey))
                .refreshToken(refreshTokenGenerator.generate(tokenPair.getRefreshToken(), secretKey))
                .build());

    }

    @ApiOperation("Forgot password init request")
    @PostMapping(value = AuthApiRoutes.FORGOT_PASSWORD_START, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        throw new RuntimeException("forgotPassword");
    }

    @ApiOperation("Change password request")
    @PostMapping(value = AuthApiRoutes.FORGOT_PASSWORD_CHANGE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<String> changePassword(@RequestBody ForgotPasswordChangeRequest forgotPasswordChangeRequest) {
        throw new RuntimeException("changePassword");
    }

    @ApiOperation("Login")
    @PostMapping(value = AuthApiRoutes.LOGIN, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<AuthenticationDTO> login(@RequestBody UserCredentialsDTO request) throws UserNotFoundException, UserBlockedException, AuthenticationException, TokenGenerateException {
        TokenPairDTO tokenPair = userAdapter.getAll()
                .authentication(request.getEmail(), request.getPassword());

        return SuccessApiResponse.of(AuthenticationDTO.builder()
                .accessToken(accessTokenGenerator.generate(tokenPair.getAccessToken(), secretKey))
                .refreshToken(refreshTokenGenerator.generate(tokenPair.getRefreshToken(), secretKey))
                .build());
    }

    @ApiOperation("refresh")
    @PostMapping(value = AuthApiRoutes.REFRESH)
    public SuccessApiResponse<AuthenticationDTO> refresh(@RequestBody RefreshTokenRequest request) throws InvalidTokenFormatException, UserNotFoundException, UserBlockedException, AuthenticationException, TokenGenerateException {
        TokenPairDTO tokenPair = userAdapter.getAll()
                .getNewTokenPairByRefreshToken(refreshTokenGenerator.parseToken(request.getRefreshToken(), secretKey));

        return SuccessApiResponse.of(AuthenticationDTO.builder()
                .accessToken(accessTokenGenerator.generate(tokenPair.getAccessToken(), secretKey))
                .refreshToken(refreshTokenGenerator.generate(tokenPair.getRefreshToken(), secretKey))
                .build());
    }

    @ApiOperation("logout")
    @PostMapping(value = AuthApiRoutes.LOGOUT)
    public OkApiResponse logout() throws AuthenticationException {
        UserDTO user = tokenAdapter.currentUser();
        tokenAdapter.logout(user);
        return OkApiResponse.of();
    }

}
