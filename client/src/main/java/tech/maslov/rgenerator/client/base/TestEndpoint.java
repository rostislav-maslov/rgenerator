package tech.maslov.rgenerator.client.base;

import com.rcore.domain.base.roles.BaseRoles;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.restapi.web.api.response.SuccessApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured({})
@Api(tags = "Test API", description = "API desc")
@RequiredArgsConstructor
@RestController
public class TestEndpoint {

    @ApiOperation("Test")
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessApiResponse<String> getReadyForDelivery() throws AuthenticationException, AuthorizationException {
        return SuccessApiResponse.of("test");
    }
}
