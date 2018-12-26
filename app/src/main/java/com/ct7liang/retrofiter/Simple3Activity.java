package com.ct7liang.retrofiter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface Network3Service{
    /**
     * @Body 示例
     *   用于非表单请求体
     */
    @POST("loginController.do?checkuser")
    Call<ResponseBody> login1(@Body Loginer user);
}

public class Simple3Activity extends AppCompatActivity {

    private Network3Service network3Service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple3);

        network3Service = MyApp.retrofit.create(Network3Service.class);
    }

    /**
     * @Body 示例
     */
    private void simple1(){
        Call<ResponseBody> call1 = network3Service.login1(new Loginer("admin", "123456", "pdpda"));
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

class Loginer{
    private String username;
    private String password;
    private String pid;

    public Loginer(String username, String password, String pid) {
        this.username = username;
        this.password = password;
        this.pid = pid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}


