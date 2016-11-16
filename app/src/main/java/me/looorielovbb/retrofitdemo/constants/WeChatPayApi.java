package me.looorielovbb.retrofitdemo.constants;

import me.looorielovbb.retrofitdemo.model.WxOrder;
import me.looorielovbb.retrofitdemo.model.WxResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Lulei on 2016/11/11.
 * time : 14:32
 * date : 2016/11/11
 * mail to lulei4461@gmail.com
 */

public interface WeChatPayApi {
    public static final String APP_ID = "wx8926eb07b90768c8"; //wx9a7f23838d78fa8f
    public String Host = "http://digirun.vicp.io:30200/";
    public String WeChatHost = "http://wxpay.weixin.qq.com/";

    //微信默认获取订单地址地址
    @GET("pub_v2/app/app_pay.php?plat=android")
    Call<WxOrder> getWxOrderInfo();

    //    payType:"90303"(固定值)
    //    orderNo:订单编号
    //    totalFee：金额
    //    subject：订单描述
    @FormUrlEncoded
    @POST("pay/payment")
    Call<WxResponse> getOrderInfo(@Field("payType") String payType,
                                  @Field("orderNo") String orderNo,
                                  @Field("totalFee") String totalFee,
                                  @Field("subject") String subject);

}
