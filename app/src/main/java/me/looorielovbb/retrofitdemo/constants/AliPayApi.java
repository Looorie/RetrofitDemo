package me.looorielovbb.retrofitdemo.constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Lulei on 2016/11/15.
 * time : 14:55
 * date : 2016/11/15
 * mail to lulei4461@gmail.com
 */

public interface AliPayApi {

    public static final String APP_ID = "2014101500013665";
    public static final String Host = "http://192.168.1.248:9090/";
    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE =
            "MIICXQIBAAKBgQDEZeKOQ4lUhGdFNtUaLMWO8KeAtNBRXtO7nRM3sZyDbPFb83DU" +
                    "1BOUHdk7JJZvTPQN20rmD/1HzjGDhnUFZHFBKlbZzHFY1ysdWEOBpdTmPN7GGrRH" +
                    "oKtMNnU7+IwW13rmn2m70utfrhbGhz21BMqQUFI9j/r94bFIhWWO6O6pwwIDAQAB" +
                    "AoGAdeFRYmJXA0uVUI3gyp0k1u7LGw1sG+r22x0kc8E/WAMDu1Ua3COOvNBNmpoT" +
                    "USq3Mbn18rCWLYstXOxPQLVQ4+b75lYOTO3WjIV7zSyMoB6QqJPzLXuq/Do9Cg/K" +
                    "6pEHSmEDMXkGQKa4SEi/bXOmLKf6wjQU336bWShOA3YjXsECQQDjNwAyAUJpCto/" +
                    "9nZgFajxmpa8dzg5cN/KlRwM8J3AZRfL6U47xzoeKvOjVToL5K4I3JXUfLDFgrX+" +
                    "F8zJvNG1AkEA3UdvsRuRYQkGrJPwMl4On6hPaOMix1X3WM/M81Uea61y7kdMIz94" +
                    "NTgau3TA0o4Z//qqSoUZLerWmtVPIw4YlwJBAK2ec3nCWJqnfknt7FpTcrrbI19z" +
                    "wfSyeW71LW/91K0VEm/wIKWxAInYzD78etRns0x2Mc8iUHk7/hDg4s0siRkCQFeR" +
                    "IIs2/KYo20JKXSChhin6gkpgsG+v/m1f9pxJD/vOrQ/MmT3NrTVqWXHbdz8bywA4" +
                    "41EQMPGx4HFvfpzBxEsCQQCd65YGX3VN8AJniuKA3b14ieQbx0t9TtuqI7jCA9mH" +
                    "twl+TQvKL72mMAQ4C4hvRUPSq3HNdaaxKoRijgpfZGza";

    /*
    * ?payType=90103
    * &orderNo='+orderNo+'
    * &totalFee='+totalFee+'
    * &subject='+subject+'
    * &showUrl='+showUrl+'
    * &returnUrl='+returnUrl+'
    * &extraCommonParam='+extraCommonParam,
    *
    * payType=90103
    * orderNo 订单编号，
    * totalFee 费用，
    * subject="订单支付",
    * showUrl="http://fx.dobado.cn/my_order.html";
    * returnUrl="http://fx.dobado.cn/my_order.html";
    * extraCommonParam="4028817957e2265d0157e4d2dbb40000"
    *
    * */
    @FormUrlEncoded
    @POST("pay/payment")
    Call<ResponseBody> getSign(@Field("payType") String payType, @Field("orderNo") String orderNo,
                               @Field("totalFee") String totalFee, @Field("subject") String subject,
                               @Field("showUrl") String showUrl,
                               @Field("returnUrl") String returnUrl,
                               @Field("extraCommonParam") String extraCommonParam);

}
