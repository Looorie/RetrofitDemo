package me.looorielovbb.retrofitdemo.model;

/**
 * Created by Lulei on 2016/11/15.
 * time : 17:07
 * date : 2016/11/15
 * mail to lulei4461@gmail.com
 */

public class AliResponse {

    /**
     * code : 0
     * data : {"appSign":"di8u1Fb46pz8fuGIhJ26fzh1RWsJKl9vytbI4
     * +XCiKVyvTyxcFcIyCcPKMV5HB3TyDgiosaGA+4FPVAUH52M/nOj19XN++R1qxXyOJX0G11AsDQml
     * +/VGuSBOUMoMrEDpQNFUYQcTHrR86Z2IFRCJJbRY4QTx9/SZIGhiYCJGe8=","resMethod":"get",
     * "resUrl":"https://mapi.alipay.com/gateway
     * .do?_input_charset=utf-8&_input_charset=utf-8&subject=hahhah&extra_common_param
     * =4028817957e2265d0157e4d2dbb40000&sign=053d03326a70f77df61aad5f6643298e&notify_url=http%3A
     * %2F%2Fdigirun.vicp.io%3A34000%2F%2Fnotice%2FalipayNotice&body=AlipayWap&payment_type=1
     * &out_trade_no=2121111&partner=2088701421932441&total_fee=0.01&return_url=http%3A%2F%2Ffx
     * .dobado.cn%2Fmy_order.html&sign_type=MD5&seller_id=2088701421932441&show_url=http%3A%2F
     * %2Ffx.dobado.cn%2Fmy_order.html"}
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
         * appSign : di8u1Fb46pz8fuGIhJ26fzh1RWsJKl9vytbI4+XCiKVyvTyxcFcIyCcPKMV5HB3TyDgiosaGA
         * +4FPVAUH52M/nOj19XN++R1qxXyOJX0G11AsDQml+/VGuSBOUMoMrEDpQNFUYQcTHrR86Z2IFRCJJbRY4QTx9
         * /SZIGhiYCJGe8=
         * resMethod : get
         * resUrl : https://mapi.alipay.com/gateway
         * .do?_input_charset=utf-8&_input_charset=utf-8&subject=hahhah&extra_common_param
         * =4028817957e2265d0157e4d2dbb40000&sign=053d03326a70f77df61aad5f6643298e&notify_url
         * =http%3A%2F%2Fdigirun.vicp
         * .io%3A34000%2F%2Fnotice%2FalipayNotice&body=AlipayWap&payment_type=1&out_trade_no
         * =2121111&partner=2088701421932441&total_fee=0.01&return_url=http%3A%2F%2Ffx.dobado
         * .cn%2Fmy_order.html&sign_type=MD5&seller_id=2088701421932441&show_url=http%3A%2F%2Ffx
         * .dobado.cn%2Fmy_order.html
         */

        private String appSign;
        private String resMethod;
        private String resUrl;

        public String getAppSign() { return appSign;}

        public void setAppSign(String appSign) { this.appSign = appSign;}

        public String getResMethod() { return resMethod;}

        public void setResMethod(String resMethod) { this.resMethod = resMethod;}

        public String getResUrl() { return resUrl;}

        public void setResUrl(String resUrl) { this.resUrl = resUrl;}
    }
}
