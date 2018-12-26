package com.ct7liang.retrofiter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

interface Network4Service{

    /**
     * @Field 示例
     *     注意: @Field @FormUrlEncoded 需要一起使用,缺一不可
     */
    @FormUrlEncoded
    @POST("loginController.do?checkuser")
    Call<ResponseBody> login1(@Field("username") String username, @Field("password") String password);

    /**
     * @FieldMap 示例
     *     注意: @FieldMap @FormUrlEncoded 需要一起使用,缺一不可
     *     FieldMap的接受类型是Map<String, String>, 非String类型会调用其toString方法
     */
    @FormUrlEncoded
    @POST("loginController.do?checkuser")
    Call<ResponseBody> login2(@FieldMap Map<String, Object> map);

    /**
     * @Query 示例
     */
    @POST("loginController.do?checkuser")
    Call<ResponseBody> login3(@Query("username") String username, @Query("password") String password);

    /**
     * @QueryMap 示例
     *      QueryMap的接受类型是Map<String, String>, 非String类型会调用其toString方法
     */
    @FormUrlEncoded
    @POST("loginController.do?checkuser")
    Call<ResponseBody> login4(@QueryMap Map<String, Object> map);

}

public class Simple4Activity extends AppCompatActivity {

    private Network4Service network4Service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple4);

        network4Service = MyApp.retrofit.create(Network4Service.class);
    }

    /**
     * @Field示例
     */
    private void simple1(){
        Call<ResponseBody> call = network4Service.login1("admin", "123456");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @FieldMap示例
     */
    private void simple2(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "123456");
        Call<ResponseBody> call = network4Service.login2(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @Query示例
     */
    private void simple3(){
        Call<ResponseBody> call = network4Service.login3("admin", "123456");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @QueryMap示例
     */
    private void simple4(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "123456");
        Call<ResponseBody> call = network4Service.login4(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
