package me.looorielovbb.retrofitdemo.constants;

import me.looorielovbb.retrofitdemo.model.WxOrder;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Lulei on 2016/11/11.
 * time : 14:32
 * date : 2016/11/11
 * mail to lulei4461@gmail.com
 */

public interface WeChatPayApi {
    public static String APP_ID = "wx8926eb07b90768c8";
    public String WeChatHost = "http://wxpay.weixin.qq.com/";

    @GET("pub_v2/app/app_pay.php?plat=android")
    Call<WxOrder> getWxOrderInfo();

}
