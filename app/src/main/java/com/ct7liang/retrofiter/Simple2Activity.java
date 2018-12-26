package com.ct7liang.retrofiter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface Network2Service{
    /**
     * @Headers 示例
     * @Header 示例
     *   请求头参数
     *   @Headers 常用于添加固定值 位置:方法注解
     *   @Header 常用于添加不固定值 位置:参数注解
     */
    @POST("loginController.do?checkuser")
    @Headers({"header1: value1", "header2: value2"})
    Call<ResponseBody> login1(@Header("header3") String value3, @Query("username") String username, @Query("password") String password);
}

public class Simple2Activity extends AppCompatActivity {

    private Network2Service network2Service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);

        network2Service = MyApp.retrofit.create(Network2Service.class);
    }

    /**
     * 示例: @Headers @Header
     */
    private void simple1(){
        Call<ResponseBody> call1 = network2Service.login1("value3", "admin", "123456");
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
