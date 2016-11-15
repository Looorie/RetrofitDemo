package me.looorielovbb.retrofitdemo.model;

/**
 * Created by Lulei on 2016/11/14.
 * time : 11:36
 * date : 2016/11/14
 * mail to lulei4461@gmail.com
 */

public class OrderResponse {


    /**
     * code : 0
     * data : {"appid":null,"noncestr":null,"package_":"Sign=WXPay","partnerid":"1383614802",
     * "prepayid":null,"resMethod":"get","resUrl":null,"sign":null,"timestamp":"1479113010"}
     * msg :
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() { return code;}

    public void setCode(int code) { this.code = code;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    public String getMsg() { return msg;}

    public void setMsg(String msg) { this.msg = msg;}

    public static class DataBean {
        /**
         * appid : null
         * noncestr : null
         * package_ : Sign=WXPay
         * partnerid : 1383614802
         * prepayid : null
         * resMethod : get
         * resUrl : null
         * sign : null
         * timestamp : 1479113010
         */

        private String appid;
        private String noncestr;
        private String package_;
        private String partnerid;
        private String prepayid;
        private String resMethod;
        private String resUrl;
        private String sign;
        private String timestamp;

        public String getAppid() { return appid;}

        public void setAppid(String appid) { this.appid = appid;}

        public String getNoncestr() { return noncestr;}

        public void setNoncestr(String noncestr) { this.noncestr = noncestr;}

        public String getPackage_() { return package_;}

        public void setPackage_(String package_) { this.package_ = package_;}

        public String getPartnerid() { return partnerid;}

        public void setPartnerid(String partnerid) { this.partnerid = partnerid;}

        public String getPrepayid() { return prepayid;}

        public void setPrepayid(String prepayid) { this.prepayid = prepayid;}

        public String getResMethod() { return resMethod;}

        public void setResMethod(String resMethod) { this.resMethod = resMethod;}

        public String getResUrl() { return resUrl;}

        public void setResUrl(String resUrl) { this.resUrl = resUrl;}

        public String getSign() { return sign;}

        public void setSign(String sign) { this.sign = sign;}

        public String getTimestamp() { return timestamp;}

        public void setTimestamp(String timestamp) { this.timestamp = timestamp;}
    }
}
