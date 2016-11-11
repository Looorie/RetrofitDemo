package me.looorielovbb.retrofitdemo.wxapi.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import me.looorielovbb.retrofitdemo.constants.WeChatPayApi;


public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

        msgApi.registerApp(WeChatPayApi.APP_ID);
    }
}
