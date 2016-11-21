package me.looorielovbb.retrofitdemo.constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Lulei on 2016/11/18.
 * time : 18:05
 * date : 2016/11/18
 * mail to lulei4461@gmail.com
 */

public interface TestApi {
    String HOST = "http://192.168.1.246:8080/";
//http://192.168.1.246:8080/jrzmq-war/webapp/fund/fund-info/user-bank-pic

    /*
    @RestApiFieldAnn(title="用户ID", required=true)
	private Integer userId;  			//用户id

	@RestApiFieldAnn(title="银行账户id", required=true)
	private Integer bankAccountId;		//银行账户id

	@RestApiFieldAnn(title="身份证正面", required=true)
	private String idCardUp;			//身份证正面

	@RestApiFieldAnn(title="身份证反面", required=true)
	private String idCardDown;			//身份证反面

	@RestApiFieldAnn(title="手持身份证正面", required=true)
	private String userWithIdCard;		//手持身份证正面

	@RestApiFieldAnn(title="手持银行卡正面", required=true)
	private String userWithBankCard;	//手持银行卡正面

	@RestApiFieldAnn(title="图片类型", required=true)
	private String type;		//? .jpg
     */

    @FormUrlEncoded
    @POST("jrzmq-war/webapp/fund/fund-info/user-bank-pic")
    Call<ResponseBody> tpic(@Field("userId") String userId,
                            @Field("bankAccountId") String bankAccountId,
                            @Field("idCardUp") String totalFee, @Field("idCardDown") String subject,
                            @Field("userWithIdCard") String userWithIdCard,
                            @Field("userWithBankCard") String userWithBankCard,
                            @Field("type") String type);
}
