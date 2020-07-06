package tech.maslov.rgenerator.domain.generator.usecase.all;

import com.rcore.domain.file.entity.FileEntity;
import com.rcore.domain.file.port.FileRepository;
import com.rcore.domain.file.port.FileStorage;
import com.rcore.domain.token.exception.AuthenticationException;
import com.rcore.domain.token.exception.AuthorizationException;
import com.rcore.domain.user.port.UserRepository;
import retrofit2.Response;
import tech.maslov.rgenerator.domain.developer.entity.DeveloperEntity;
import tech.maslov.rgenerator.domain.developer.port.DeveloperRepository;
import tech.maslov.rgenerator.domain.developer.service.github.GitHubService;
import tech.maslov.rgenerator.domain.developer.service.github.dto.ContentResponse;
import tech.maslov.rgenerator.domain.developer.service.github.dto.RepoResponse;
import tech.maslov.rgenerator.domain.developer.usecase.all.AuthorizationDevByTokenUseCase;
import tech.maslov.rgenerator.domain.generator.dto.CheckRepoDTO;
import tech.maslov.rgenerator.domain.generator.entity.FileStructure;
import tech.maslov.rgenerator.domain.generator.entity.GeneratorEntity;
import tech.maslov.rgenerator.domain.generator.port.GeneratorRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class GeneratorGitHubUseCase extends GeneratorBaseUseCase {

    private final FileStorage fileStorage;
    private final FileRepository fileRepository;
    private final GeneratorEditUseCase generatorEditUseCase;

    public GeneratorGitHubUseCase(GeneratorRepository generatorRepository, AuthorizationDevByTokenUseCase authorizationDevByTokenUseCase, DeveloperRepository developerRepository, UserRepository userRepository, FileStorage fileStorage, FileRepository fileRepository, GeneratorEditUseCase generatorEditUseCase) {
        super(generatorRepository, authorizationDevByTokenUseCase, developerRepository, userRepository);
        this.fileStorage = fileStorage;
        this.fileRepository = fileRepository;
        this.generatorEditUseCase = generatorEditUseCase;
    }

    public List<RepoResponse> repos() throws AuthenticationException, IOException {
        DeveloperEntity developerEntity = authorizationDevByTokenUseCase.currentDeveloper().get();
        if (developerEntity.getGitHubAccessToken() == null) throw new IOException();

        Response<List<RepoResponse>> response = GitHubService.getInstance().api().getRepos(developerEntity.getGitHubAccessToken()).execute();
        if (response.isSuccessful() == false) throw new IOException();

        return response.body();
    }

    public CheckRepoDTO checkRepo(String repoName) throws AuthenticationException, IOException {
        DeveloperEntity developerEntity = authorizationDevByTokenUseCase.currentDeveloper().get();
        if (developerEntity.getGitHubAccessToken() == null) throw new IOException();

        Response<List<ContentResponse>> response = GitHubService.getInstance().api().content(repoName.split("/")[0], repoName.split("/")[1], developerEntity.getGitHubAccessToken()).execute();
        if (response.isSuccessful() == false) throw new IOException();

        List<ContentResponse> contentResponses = response.body();
        CheckRepoDTO checkRepoDTO = new CheckRepoDTO();

        for (ContentResponse contentResponse : contentResponses) {
            if (contentResponse.getType().equals("file")) {
                if (contentResponse.getName().equals("example.json")) checkRepoDTO.setExampleJson(true);
            } else {
                if (contentResponse.getName().equals("root")) checkRepoDTO.setRootDir(true);
            }
        }

        return checkRepoDTO;
    }

    private String loadContent(String url, String accessToken) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .setHeader("access_token", accessToken)
                .build();

        HttpResponse<String> responseHttp = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (responseHttp.statusCode() != 200) throw new IOException();
        return responseHttp.body();
    }

    private GeneratorEntity clearTemplates(GeneratorEntity generatorEntity) {
        GeneratorDeleteUseCase.deleteFilesInDirectory(generatorEntity.getFileStructure().getDirectory(), this.fileStorage, this.fileRepository);
        generatorEntity.setFileStructure(new FileStructure());
        return generatorEntity;
    }

    private GeneratorEntity syncDir(GeneratorEntity generatorEntity, String repoName, String accessToken, String path) throws IOException, InterruptedException, AuthenticationException, AuthorizationException {
        Response<List<ContentResponse>> response = GitHubService.getInstance().api().contentByPath(repoName.split("/")[0], repoName.split("/")[1], path, accessToken).execute();
        if (response.isSuccessful() == false) throw new IOException();

        List<ContentResponse> contentResponses = response.body();

        for (ContentResponse contentResponse : contentResponses) {
            if (contentResponse.getType().equals("file") == true) {
                // FILES
                String exampleUrl = contentResponse.getDownload_url();
                String content = loadContent(exampleUrl, accessToken);

                InputStream targetStream = new ByteArrayInputStream(content.getBytes());

                String filePath = fileStorage.store(targetStream, contentResponse.getName(), "text/plain");
                FileEntity fileEntity = new FileEntity();
                fileEntity.setFilePath(filePath);
                fileEntity.setFileName(contentResponse.getName());
                fileEntity.setIsPrivate(true);
                fileEntity = fileRepository.save(fileEntity);

                this.generatorEditUseCase.fileAdd(generatorEntity.getId(), "/" + contentResponse.getPath(), fileEntity);
                generatorEntity = generatorRepository.findById(generatorEntity.getId()).get();
            } else {
                // DIR
                generatorEntity = syncDir(generatorEntity, repoName, accessToken, contentResponse.getPath());
            }
        }

        return generatorEntity;
    }

    private GeneratorEntity syncJson(GeneratorEntity generatorEntity, String repoName, String accessToken) throws IOException, InterruptedException, AuthenticationException, AuthorizationException {
        Response<List<ContentResponse>> response = GitHubService.getInstance().api().content(repoName.split("/")[0], repoName.split("/")[1], accessToken).execute();
        if (response.isSuccessful() == false) throw new IOException();

        List<ContentResponse> contentResponses = response.body();

        for (ContentResponse contentResponse : contentResponses) {
            if (contentResponse.getName().equals("example.json")) {
                String exampleUrl = contentResponse.getDownload_url();
                generatorEntity.setExample(loadContent(exampleUrl, accessToken));
                generatorRepository.save(generatorEntity);
            }

            if (contentResponse.getName().equals("root") && contentResponse.getType().equals("file") == false) {
                // Start load files
                generatorEntity = this.clearTemplates(generatorEntity);
                generatorEntity = generatorRepository.save(generatorEntity);
                generatorEntity = this.syncDir(generatorEntity, repoName, accessToken,  contentResponse.getPath());
            }
        }

        return generatorEntity;
    }

    public void syncRepo(String generatorId, String repoName) throws AuthenticationException, IOException, AuthorizationException, InterruptedException {
        DeveloperEntity developerEntity = authorizationDevByTokenUseCase.currentDeveloper().get();
        if (developerEntity.getGitHubAccessToken() == null) throw new IOException();
        if (developerEntity.getGitHubAccessToken() == null) throw new IOException();

        GeneratorEntity generatorEntity = generatorRepository.findById(generatorId).get();
        checkEditAccess(generatorEntity);

        syncJson(generatorEntity, repoName, developerEntity.getGitHubAccessToken());

        generatorEntity = generatorRepository.findById(generatorId).get();
        generatorEntity.setDidUseGitHub(true);
        generatorRepository.save(generatorEntity);
    }
}
