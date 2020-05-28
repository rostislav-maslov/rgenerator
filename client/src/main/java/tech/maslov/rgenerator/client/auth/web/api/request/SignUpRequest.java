package tech.maslov.rgenerator.client.auth.web.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String login;
    private String email;
    private String password;
}
