package tech.maslov.rgenerator.domain.developer.service.github;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tech.maslov.rgenerator.domain.developer.service.github.dto.ContentResponse;
import tech.maslov.rgenerator.domain.developer.service.github.dto.RepoResponse;

import java.util.List;

public interface GithubApi {

    //Listing
    //https://api.github.com/repos/rostislav-maslov/rgenerator/contents?ref=master&access_token=436cfed0a8242ba5b21f2bf70201bba06d10457a
    @GET("/repos/{name}/{repo}/contents")
    public Call<List<ContentResponse>> content(@Path("name") String name, @Path("repo") String repo, @Query("access_token") String access_token);

    @GET("/repos/{name}/{repo}/contents/{path}")
    public Call<List<ContentResponse>> contentByPath(@Path("name") String name, @Path("repo") String repo, @Path("path") String path, @Query("access_token") String access_token);

    //Repos
    //https://api.github.com/user/repos?access_token=436cfed0a8242ba5b21f2bf70201bba06d10457a
    @GET("/user/repos")
    public Call<List<RepoResponse>> getRepos(@Query("access_token") String access_token);

    //AccessToken
    //https://github.com/login/oauth/access_token?client_id=&client_secret=&code=&state=repo public_repo
    @POST("/login/oauth/access_token")
    public Call<String> getAccessToken(
            @Query("client_id") String client_id,
            @Query("client_secret") String client_secret,
            @Query("code") String code,
            @Query("state") String state
    );
}
