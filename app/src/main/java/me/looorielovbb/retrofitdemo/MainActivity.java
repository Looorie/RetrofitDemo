package me.looorielovbb.retrofitdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.looorielovbb.retrofitdemo.constants.AliPayApi;
import me.looorielovbb.retrofitdemo.constants.GitHubApi;
import me.looorielovbb.retrofitdemo.constants.WeChatPayApi;
import me.looorielovbb.retrofitdemo.model.AliResponse;
import me.looorielovbb.retrofitdemo.model.AuthResult;
import me.looorielovbb.retrofitdemo.model.Contributor;
import me.looorielovbb.retrofitdemo.model.PayResult;
import me.looorielovbb.retrofitdemo.model.WxOrder;
import me.looorielovbb.retrofitdemo.model.WxResponse;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.looorielovbb.retrofitdemo.MainActivity.AlipayHandler.SDK_PAY_FLAG;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    Call<ResponseBody> call;
    Call<WxResponse> call2;

    Call<List<Contributor>> call1;
    Toolbar toolbar;
    ProgressDialog dialog;
    OkHttpClient okHttpClient;
    Call<WxOrder> wxOrderCall;
    EditText t1, t2;
    TextView t3;
    private AlipayHandler alipayHandler = new AlipayHandler(this);
    private IWXAPI iwxapi;

    @TargetApi(android.os.Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (EditText) findViewById(R.id.t1);
        t2 = (EditText) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("支付测试");
        setSupportActionBar(toolbar);
        initOkhttpClient();
        initWxPay();
    }

    void initOkhttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor)
                .retryOnConnectionFailure(true).connectTimeout(10, TimeUnit.SECONDS).build();
    }

    private void initWxPay() {
        iwxapi = WXAPIFactory.createWXAPI(this, WeChatPayApi.APP_ID);
        iwxapi.registerApp(WeChatPayApi.APP_ID);
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
        dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);
        retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        GitHubApi api = retrofit.create(GitHubApi.class);

        call1 = api.contributorsAndAddHeader("square", "retrofit");
        call1.enqueue(new Callback<List<Contributor>>() {

            @Override
            public void onResponse(Call<List<Contributor>> call,
                                   Response<List<Contributor>> response) {
                dialog.dismiss();
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
                dialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrofit1(View view) {
        dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);
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
                dialog.dismiss();
                try {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT)
                            .show();
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
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
                dialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void wxorder(View view) {
        dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);

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

    public void getOrder(View view) {
        if (!check()) { return; }
        dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);

        retrofit = new Retrofit.Builder().baseUrl(WeChatPayApi.Host)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        WeChatPayApi wxAPI = retrofit.create(WeChatPayApi.class);
        call2 = wxAPI.getOrderInfo("90303", t1.getText().toString().trim(),
                t2.getText().toString().trim(), "测试");
        call2.enqueue(new Callback<WxResponse>() {
            @Override
            public void onResponse(Call<WxResponse> call, Response<WxResponse> response) {

                dialog.dismiss();
                if (response.isSuccessful()) {
                    WxResponse orderResponse = response.body();
                    Gson gson = new Gson();
                    t3.setText(gson.toJson(orderResponse));
                    if (orderResponse.getCode() == 0) {
                        WxResponse.DataBean dataBean = orderResponse.getData();

                        PayReq request = new PayReq();
                        request.appId = dataBean.getAppid();
                        request.partnerId = dataBean.getPartnerid();
                        request.prepayId = dataBean.getPrepayid();
                        request.packageValue = dataBean.getPackage_();
                        request.nonceStr = dataBean.getNoncestr();
                        request.timeStamp = dataBean.getTimestamp();
                        request.sign = dataBean.getSign();
                        if (iwxapi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT) {
                            Toast.makeText(MainActivity.this, "可支付", Toast.LENGTH_SHORT).show();
                            iwxapi.sendReq(request);
                        } else {
                            Toast.makeText(MainActivity.this, "不支持支付", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "返回信息：" + orderResponse.getMsg(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<WxResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e("onFailure", t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    boolean check() {
        if (t1.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "订单号为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (t2.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "总额", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void alisign(View view) {
        if (!check()) { return; }
        dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);

        retrofit = new Retrofit.Builder().baseUrl(AliPayApi.Host)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AliPayApi aliAPI = retrofit.create(AliPayApi.class);
        call = aliAPI
                .getSign("90103", t1.getText().toString().trim(), t2.getText().toString().trim(),
                        "测试", "http://fx.dobado.cn/my_order.html",
                        "http://fx.dobado.cn/my_order.html", "4028817957e2265d0157e4d2dbb40000");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                dialog.dismiss();
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    try {
                        final AliResponse aliResponse = gson
                                .fromJson(response.body().string(), AliResponse.class);
                        t3.setText(aliResponse.getData().getAppSign());
                        Log.e("aliResponse", aliResponse.getData().getAppSign());
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(MainActivity.this);
                                Map<String, String> result = alipay
                                        .payV2(aliResponse.getData().getAppSign(), true);
                                Log.i("msp", result.toString());

                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                alipayHandler.sendMessage(msg);
                            }
                        };

                        Thread payThread = new Thread(payRunnable);
                        payThread.start();

                    } catch (IOException e) {
                        Log.e("IOException", e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                Log.e("onFailure", "-----------------");
                Log.e("onFailure", t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    static class AlipayHandler extends Handler {
        static final int SDK_PAY_FLAG = 1;
        static final int SDK_AUTH_FLAG = 2;
        private WeakReference<Activity> mOuter;

        AlipayHandler(Activity activity) {
            mOuter = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity outer = mOuter.get();
            if (outer != null) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            Toast.makeText(outer, "支付成功", Toast.LENGTH_SHORT).show();
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(outer, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case SDK_AUTH_FLAG: {
                        @SuppressWarnings("unchecked") AuthResult authResult = new AuthResult(
                                (Map<String, String>) msg.obj, true);
                        String resultStatus = authResult.getResultStatus();

                        // 判断resultStatus 为“9000”且result_code
                        // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                        if (TextUtils.equals(resultStatus, "9000") &&
                                TextUtils.equals(authResult.getResultCode(), "200")) {
                            // 获取alipay_open_id，调支付时作为参数extern_token 的value
                            // 传入，则支付账户为该授权账户
                            Toast.makeText(outer, "授权成功\n" +
                                            String.format("authCode:%s", authResult.getAuthCode()),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他状态值则为授权失败
                            Toast.makeText(outer,
                                    "授权失败" + String.format("authCode:%s", authResult.getAuthCode()),
                                    Toast.LENGTH_SHORT).show();

                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        }

    }

}
