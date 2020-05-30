package tech.maslov.rgenerator.client.user.web.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MeResponse {
    private String id;
    private String email;
    private String login;
    private Boolean githubConnected = false;
}
