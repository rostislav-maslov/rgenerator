package tech.maslov.rgenerator.domain.developer.service.github;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GitHubService {
    private static GitHubService mInstance;
    private static final String API_URL = "https://api.github.com";
    private static final String AUTH_URL = "https://github.com";
    private Retrofit mRetrofitApi;
    private Retrofit mRetrofitAuth;

    private GitHubService() {
        mRetrofitApi = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitAuth = new Retrofit.Builder()
                .baseUrl(AUTH_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public static GitHubService getInstance() {
        if (mInstance == null) {
            mInstance = new GitHubService();
        }
        return mInstance;
    }

    public GithubApi api() {
        return mRetrofitApi.create(GithubApi.class);
    }
    public GithubApi auth() {
        return mRetrofitAuth.create(GithubApi.class);
    }
}
