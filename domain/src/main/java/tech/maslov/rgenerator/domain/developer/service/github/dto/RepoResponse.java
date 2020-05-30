package tech.maslov.rgenerator.domain.developer.service.github.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoResponse {

    @Getter
    @Setter
    public static class Owner {
        private String login;
        private Long id;
        private String node_id;
        private String avatar_url;
        private String gravatar_id;
        private String url;
        private String html_url;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private String site_admin;
    }

    private Long id;
    private String node_id;
    private String name;
    private String full_name;

}
