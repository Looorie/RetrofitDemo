package me.looorielovbb.retrofitdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.looorielovbb.retrofitdemo.network.GitHubApi;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    Retrofit retrofit;
    Call<ResponseBody> call;
    Call<List<Contributor>> call1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (call != null) {
            call.cancel();
        }
    }

    public void retrofit(View view) {
//        retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").build();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
                .retryOnConnectionFailure(true).connectTimeout(5, TimeUnit.SECONDS)
//                .addNetworkInterceptor(mTokenInterceptor)
                .build();


        retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        GitHubApi api = retrofit.create(GitHubApi.class);
//        call = api.contributorsBySimpleGetCall("square", "retrofit");
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT)
//                            .show();
//                    if (response.isSuccessful()) {
//                        Gson gson = new Gson();
//                        response.body();
//                        ArrayList<Contributor> contributorsList = gson
//                                .fromJson(response.body().string(),
//                                        new TypeToken<List<Contributor>>() {}.getType());
//                        for (Contributor contributor : contributorsList) {
//                            Log.d("login", contributor.getLogin());
//                            Log.d("contributions", contributor.getContributions() + "");
//                            Toast.makeText(MainActivity.this,
//                                    contributor.getLogin() + contributor.getContributions(),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });


//        call1 = api.contributorsByAddConverterGetCall("square", "retrofit");
        call1 = api.contributorsAndAddHeader("square", "retrofit");
        call1.enqueue(new Callback<List<Contributor>>() {

            @Override
            public void onResponse(Call<List<Contributor>> call,
                                   Response<List<Contributor>> response) {
                Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    List<Contributor> contributorList = response.body();
                    Toast.makeText(MainActivity.this, contributorList.size() + "",
                            Toast.LENGTH_SHORT).show();
                    Log.e("Tag", contributorList.toString());
                    for (Contributor contributor : contributorList) {
                        Log.e("login", contributor.getLogin());
                        Log.e("contributions", contributor.getContributions() + "");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
