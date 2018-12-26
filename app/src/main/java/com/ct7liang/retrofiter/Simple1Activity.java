package com.ct7liang.retrofiter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


interface Network1Service{

    /**
     * 以下注解分别对应HTTP的各种请求方法, 都接收一个字符串表示接口path与baseUrl组成完整的Url
     */
    @POST("loginController.do?checkuser")
    Call<ResponseBody> login1();

    @GET("loginController.do?checkuser")
    Call<ResponseBody> login2();

    @PUT("loginController.do?checkuser")
    Call<ResponseBody> login3();

    @DELETE("loginController.do?checkuser")
    Call<ResponseBody> login4();

    @PATCH("loginController.do?checkuser")
    Call<ResponseBody> login5();

    @HEAD("loginController.do?checkuser")
    Call<ResponseBody> login6();

    @OPTIONS("loginController.do?checkuser")
    Call<ResponseBody> login7();


    /**
     * HTTP注解的请求方法, 可以替代以上方法中的任意一个注解
     *  method 表示请求方法,区分大小写
     *  path 表示路径
     *  hasBody 表示是否有请求体,默认为false
     */
    @HTTP(method = "POST", path = "loginController.do?checkuser", hasBody = true)
    Call<ResponseBody> login8(@Query("username") String username, @Query("password") String password);


    /**
     * @Url注解示例
     *   方法注解里面则不需要写入访问地址了
     */
    @POST()
    Call<ResponseBody> login11(@Url String url, @Query("username") String username, @Query("password") String password);


    /**
     * @Path注解示例
     *   表示动态添加访问路径中的"{}"里面的路径内容
     */
    @POST("loginController.{path}?checkuser")
    Call<ResponseBody> login22(@Path("path") String path, @Query("username") String username, @Query("password") String password);


    /**
     *  Retrofit的Url组合规则
     *
     *  http://localhost:8080/abc/      /def                    http://localhost:8080/def
     *      如果在注解中提供的url是不完整的url，且以/开头，则请求的url为baseUrl的主机部分+注解中提供的值
     *
     *  http://localhost:8080/abc/      def                     http://localhost:8080/abc/def
     *      如果在注解中提供的url是不完整的url，且不以 / 开头，则请求的url为baseUrl+注解中提供的值
     *
     *  http://localhost:8080/abc/      http://github.com/abc   http://github.com/abc
     *      如果在注解中提供的url是完整的url，则url将作为请求的url
     */
}

public class Simple1Activity extends AppCompatActivity {

    private Network1Service network1Service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple1);

        network1Service = MyApp.retrofit.create(Network1Service.class);
    }


    /**
     * @Url注解示例
     */
    private void simple1(){
        Call<ResponseBody> call1 = network1Service.login11("loginController.do?checkuser", "admin", "123456");
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @Path注解示例
     */
    private void simple2(){
        Call<ResponseBody> call2 = network1Service.login22("do", "admin", "123456");
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}