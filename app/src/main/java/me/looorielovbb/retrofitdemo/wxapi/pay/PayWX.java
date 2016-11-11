package me.looorielovbb.retrofitdemo.wxapi.pay;


import android.content.Context;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import me.looorielovbb.retrofitdemo.constants.WeChatPayApi;


public class PayWX {

    Context context;
    private IWXAPI api;


    public void init(Context context) {
        this.context = context.getApplicationContext();
        api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(WeChatPayApi.APP_ID);
    }


    public void pay(PayReq req) {
        api.sendReq(req);
    }


}