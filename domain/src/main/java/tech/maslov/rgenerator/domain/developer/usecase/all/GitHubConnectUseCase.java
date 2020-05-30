package tech.maslov.rgenerator.domain.developer.usecase.all;

import com.rcore.domain.token.exception.AuthenticationException;
import retrofit2.Response;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.service.github.GitHubService;
import tech.maslov.rgenerator.domain.developer.service.github.dto.RepoResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class GitHubConnectUseCase {
    private final AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase;
    private final DeveloperRepository developerRepository;
    private final String gitGubClientSecret;
    private final String gitGubClientId;

    public GitHubConnectUseCase(AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, DeveloperRepository developerRepository, String gitGubClientSecret, String gitGubClientId) {
        this.authorizationDevByTokenUseCase = authorizationDevByTokenUseCase;
        this.developerRepository = developerRepository;
        this.gitGubClientSecret = gitGubClientSecret;
        this.gitGubClientId = gitGubClientId;
    }

    private String getAccessToken(String code) throws IOException {

        String state = "repo public_repo";

        Response<String> response = GitHubService.getInstance().auth().getAccessToken(gitGubClientId, gitGubClientSecret, code, state).execute();

        // print status code
        if (response.isSuccessful() != true) {
            throw new IOException();
        }

        // access_token=731bb0f992275e6fbe9fa0d8b391b46a7577afcb&scope=repo&token_type=bearer
        String responseString = response.body();
        String responseArray[] = responseString.split("&");
        String accessToken = responseArray[0].replaceAll("access_token=", "");
        return accessToken;
    }

    public DeveloperEntity connect(String code) throws AuthenticationException, IOException {
        DeveloperEntity developerEntity = authorizationDevByTokenUseCase.currentDeveloper().get();
        String accessToken = getAccessToken(code);
        developerEntity.setGitHubAccessToken(accessToken);
        developerRepository.save(developerEntity);

        return developerEntity;
    }


}
