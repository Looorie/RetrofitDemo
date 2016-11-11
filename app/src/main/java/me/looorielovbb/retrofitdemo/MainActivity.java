package me.looorielovbb.retrofitdemo;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.looorielovbb.retrofitdemo.constants.GitHubApi;
import me.looorielovbb.retrofitdemo.constants.WeChatPayApi;
import me.looorielovbb.retrofitdemo.model.Contributor;
import me.looorielovbb.retrofitdemo.model.WxOrder;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    Call<ResponseBody> call;
    Call<List<Contributor>> call1;
    Toolbar toolbar;
    ProgressDialog dialog;

    Call<WxOrder> wxOrderCall;

    private IWXAPI iwxapi;

    @TargetApi(android.os.Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("微信支付测试");
        setSupportActionBar(toolbar);

        iwxapi = WXAPIFactory.createWXAPI(this, WeChatPayApi.APP_ID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (call != null) {
            call.cancel();
        }

        if (call1 != null) {
            call1.cancel();
        }
        if (wxOrderCall != null) {
            wxOrderCall.cancel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_wx_reg:
                iwxapi.registerApp(WeChatPayApi.APP_ID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void retrofit(View view) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
                .retryOnConnectionFailure(true).connectTimeout(5, TimeUnit.SECONDS).build();


        retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        GitHubApi api = retrofit.create(GitHubApi.class);

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

    public void retrofit1(View view) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
                .retryOnConnectionFailure(true).connectTimeout(5, TimeUnit.SECONDS)
//                .addNetworkInterceptor(mTokenInterceptor)
                .build();

        retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient)
                .build();
        GitHubApi api = retrofit.create(GitHubApi.class);
        call = api.contributorsBySimpleGetCall("square", "retrofit");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT)
                            .show();
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        response.body();
                        ArrayList<Contributor> contributorsList = gson
                                .fromJson(response.body().string(),
                                        new TypeToken<List<Contributor>>() {}.getType());
                        for (Contributor contributor : contributorsList) {
                            Log.d("login", contributor.getLogin());
                            Log.d("contributions", contributor.getContributions() + "");
                            Toast.makeText(MainActivity.this,
                                    contributor.getLogin() + contributor.getContributions(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void wxorder(View view) {
        dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
                .retryOnConnectionFailure(true).connectTimeout(10, TimeUnit.SECONDS).build();


        retrofit = new Retrofit.Builder().baseUrl(WeChatPayApi.WeChatHost)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        WeChatPayApi wxAPI = retrofit.create(WeChatPayApi.class);

        wxOrderCall = wxAPI.getWxOrderInfo();
        wxOrderCall.enqueue(new Callback<WxOrder>() {

            @Override
            public void onResponse(Call<WxOrder> call, Response<WxOrder> response) {
                dialog.dismiss();

                if (response.isSuccessful()) {

                    WxOrder order = response.body();
                    PayReq wxPayRequest = new PayReq();
                    wxPayRequest.appId = order.getAppid();
                    wxPayRequest.partnerId = order.getPartnerid();
                    wxPayRequest.prepayId = order.getPrepayid();
                    wxPayRequest.nonceStr = order.getNoncestr();
                    wxPayRequest.timeStamp = order.getTimestamp() + "";
                    wxPayRequest.packageValue = order.getPackageValue();
                    wxPayRequest.sign = order.getSign();

                    if (iwxapi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT) {
                        Toast.makeText(MainActivity.this, "可支付", Toast.LENGTH_SHORT).show();
                        iwxapi.sendReq(wxPayRequest);
                    } else {
                        Toast.makeText(MainActivity.this, "不支持支付", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<WxOrder> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
