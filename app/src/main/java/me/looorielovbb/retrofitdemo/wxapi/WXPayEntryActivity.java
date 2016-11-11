package me.looorielovbb.retrofitdemo.wxapi;


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

import me.looorielovbb.retrofitdemo.R;
import me.looorielovbb.retrofitdemo.constants.WeChatPayApi;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private Activity activity = this;
    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, WeChatPayApi.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0:
                    Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    break;
                case -2:
                    break;
            }
        }
    }
}