package tech.maslov.rgenerator.client.templateResult.web;

import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.restapi.web.api.response.SuccessApiResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.maslov.rgenerator.adapter.templateResult.TemplateResultAdapter;
import tech.maslov.rgenerator.client.templateResult.application.TemplateWebMapper;
import tech.maslov.rgenerator.client.templateResult.web.api.TemplateResultCreateRequest;
import tech.maslov.rgenerator.client.templateResult.web.api.TemplateResultResponse;

@Api(tags = "Template result API")
@RequiredArgsConstructor
@RestController
public class TemplateResultEndpoints {
    private final TemplateResultAdapter templateResultAdapter;
    private final TemplateWebMapper templateWebMapper;

    @RequestMapping(value = TemplateResultApiRouter.ROOT, method = RequestMethod.POST)
    public SuccessApiResponse<TemplateResultResponse> create(@RequestBody TemplateResultCreateRequest request) throws AuthenticationException, AuthorizationException {
        try {
            return SuccessApiResponse.of(
                    templateWebMapper.map(
                            templateResultAdapter.zip.zip(request.getGenerateId(), request.getContent())
                    )
            );
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
