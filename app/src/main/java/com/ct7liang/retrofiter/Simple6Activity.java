package com.ct7liang.retrofiter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface Network6Service{

    /**
     * 默认情况下Retrofit只支持将HTTP的响应体转换为ResponseBody
     */
    @POST("loginController.do?checkuser")
    Call<ResponseBody> login1(@Query("userName") String username, @Query("password") String password, @Query("pid") String pid);

    /**
     * Converter数据转换
     */
    @POST("loginController.do?checkuser")
    Call<User> login2(@Query("userName") String username, @Query("password") String password, @Query("pid") String pid);
}

public class Simple6Activity extends AppCompatActivity {

    private Network6Service network6Service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple6);

        network6Service = MyApp.retrofit.create(Network6Service.class);

        simple2();
    }

    /**
     * 示例: 默认情况
     */
    private void simple1(){
        Call<ResponseBody> call = network6Service.login1("admin", "123456", "pdpda");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //获取数据,转换为字符串
                    String string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 示例: 自定义转换数据类型
     */
    private void simple2(){
        Call<User> call = network6Service.login2("admin", "123456", "pdpda");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //获取转换后的数据模型
                User body = response.body();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}


class User{

    private boolean success;
    private String jsonStr;
    private String msg;
    private Object obj;
    private AttributesBean attributes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public static class AttributesBean {

        private String sessionid;

        public String getSessionid() {
            return sessionid;
        }

        public void setSessionid(String sessionid) {
            this.sessionid = sessionid;
        }
    }
}