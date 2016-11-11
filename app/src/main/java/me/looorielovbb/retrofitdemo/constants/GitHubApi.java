package me.looorielovbb.retrofitdemo.constants;

import java.util.List;

import me.looorielovbb.retrofitdemo.model.Contributor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Lulei on 2016/11/10.
 * time : 19:37
 * date : 2016/11/10
 * mail to lulei4461@gmail.com
 */

public interface GitHubApi {

    @GET("repos/{owner}/{repo}/contributors")
    Call<ResponseBody> contributorsBySimpleGetCall(@Path("owner") String owner,
                                                   @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributorsByAddConverterGetCall(@Path("owner") String owner,
                                                              @Path("repo") String repo);

    @GET("users/{user}/repos")
    Call<ResponseBody> listRepos(@Path("user") String user);

    @Headers({"Accept: application/vnd.github.v3.full+json",
                     "User-Agent: RetrofitBean-Sample-App",
                     "name:ljd"})
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributorsAndAddHeader(@Path("owner") String owner,
                                                     @Path("repo") String repo);
}
