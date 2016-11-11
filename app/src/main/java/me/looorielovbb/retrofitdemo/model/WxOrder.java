package me.looorielovbb.retrofitdemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lulei on 2016/11/11.
 * time : 15:12
 * date : 2016/11/11
 * mail to lulei4461@gmail.com
 */

public class WxOrder {

    /**
     * appid : wxb4ba3c02aa476ea1
     * partnerid : 1305176001
     * package : Sign=WXPay
     * noncestr : ed320083d646f59e0f76b0314e2736e7
     * timestamp : 1478847426
     * prepayid : wx2016111114570680f15e899b0136977432
     * sign : EDB5C0368B9A3C273E14B75A743B834E
     */

    private String appid;
    private String partnerid;
    @SerializedName("package") private String packageValue;
    private String noncestr;
    private int timestamp;
    private String prepayid;
    private String sign;

    public String getAppid() { return appid;}

    public void setAppid(String appid) { this.appid = appid;}

    public String getPartnerid() { return partnerid;}

    public void setPartnerid(String partnerid) { this.partnerid = partnerid;}

    public String getPackageValue() { return packageValue;}

    public void setPackageValue(String packageValue) { this.packageValue = packageValue;}

    public String getNoncestr() { return noncestr;}

    public void setNoncestr(String noncestr) { this.noncestr = noncestr;}

    public int getTimestamp() { return timestamp;}

    public void setTimestamp(int timestamp) { this.timestamp = timestamp;}

    public String getPrepayid() { return prepayid;}

    public void setPrepayid(String prepayid) { this.prepayid = prepayid;}

    public String getSign() { return sign;}

    public void setSign(String sign) { this.sign = sign;}
}
