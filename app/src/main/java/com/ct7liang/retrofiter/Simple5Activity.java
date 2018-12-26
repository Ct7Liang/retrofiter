package com.ct7liang.retrofiter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

interface Network5Service{

    @POST("/rest/mediaFilec/uploadMediaFile")
    @Multipart
    Call<ResponseBody> upload(@Query("fileName") String name, @Query("createBy") String user, @Part MultipartBody.Part file);


    /**
     * @Multipart
     * @Part
     * 两者缺一不可,常用于文件上传
     */
    @POST("/rest/mediaFilec/uploadMediaFile")
    @Multipart
    Call<ResponseBody> upload1(@Part("fileName") RequestBody name, @Part("createBy") RequestBody user, @Part MultipartBody.Part file);


    /**
     * @Multipart
     * @PartMap
     * 两者缺一不可,常用于文件上传
     */
    @POST("/rest/mediaFilec/uploadMediaFile")
    @Multipart
    Call<ResponseBody> upload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);
}

public class Simple5Activity extends AppCompatActivity {

    private Network5Service network5Service;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple5);

        file = new File("/storage/emulated/0/Music/Musicsss/刘德华 - 冰雨.mp3");

        network5Service = MyApp.retrofit.create(Network5Service.class);
    }


    private void simple(){
        MediaType mediaType = MediaType.parse("application/octet-stream");

        RequestBody requestBody3 = RequestBody.create(mediaType, file);

        //此方法第二个参数是上传文件名称的后缀
        MultipartBody.Part bodyPart = MultipartBody.Part.createFormData("file", "mp3", requestBody3);

        Call<ResponseBody> call = network5Service.upload("媒体文件名称", "用户id", bodyPart);
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
     * 示例:
     * @Multipart
     * @Part
     */
    private void simple1(){
        MediaType textType = MediaType.parse("text/plain");

        RequestBody requestBody1 = RequestBody.create(textType, "媒体文件名称");
        RequestBody requestBody2 = RequestBody.create(textType, "用户id");

        MediaType mediaType = MediaType.parse("application/octet-stream");

        RequestBody requestBody3 = RequestBody.create(mediaType, file);

        //此方法第二个参数是上传文件名称的后缀
        MultipartBody.Part bodyPart = MultipartBody.Part.createFormData("file", "mp3", requestBody3);

        Call<ResponseBody> call = network5Service.upload1(requestBody1, requestBody2, bodyPart);
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
     * 示例:
     * @Multipart
     * @PartMap
     */
    private void simple2(){
        MediaType textType = MediaType.parse("text/plain");

        RequestBody requestBody1 = RequestBody.create(textType, "媒体文件名称");
        RequestBody requestBody2 = RequestBody.create(textType, "用户id");

        Map<String, RequestBody> map = new HashMap<>();
        map.put("name", requestBody1);
        map.put("user", requestBody2);

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody requestBody3 = RequestBody.create(mediaType, file);

        //此方法第二个参数是上传文件名称的后缀
        MultipartBody.Part bodyPart = MultipartBody.Part.createFormData("file", "mp3", requestBody3);

        Call<ResponseBody> call = network5Service.upload2(map, bodyPart);
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
