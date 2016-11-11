package me.looorielovbb.retrofitdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import me.looorielovbb.retrofitdemo.constants.WeChatPayApi;

public class WxPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WxPayEntryActivity";
    private Activity activity = this;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_payentry);


        api = WXAPIFactory.createWXAPI(this, WeChatPayApi.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e(TAG, "onPayFinish, errCode = " + baseResp.errCode);

        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
            } else {

            }
        }
    }
}
