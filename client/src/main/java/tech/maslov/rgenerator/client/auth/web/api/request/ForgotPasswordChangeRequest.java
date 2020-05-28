package tech.maslov.rgenerator.client.auth.web.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordChangeRequest {
    private String token;
    private String password;
}
