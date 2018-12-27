package com.ct7liang.retrofiter;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

interface Network7Service{
    /**
     * @Streaming 下载
     *   大文件官方建议用 @Streaming 来进行注解，不然会出现IO异常，小文件可以忽略不注入。
     */
    @Streaming
    @GET("http://114.115.162.234:8000/group1/M00/00/04/wKgAnlwjMzGAGfKYAEPKP5Gv-xk043.mp3")
    Call<ResponseBody> downloadMp3();

    @Streaming
    @GET("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1382061481,2378509391&fm=26&gp=0.jpg")
    Call<ResponseBody> downloadImg();

    @Streaming
    @GET("http://192.168.43.210:8080/music/2.mp4")
    Call<ResponseBody> downloadVideo();
}

public class Simple7Activity extends AppCompatActivity {

    private Network7Service network7Service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple7);

        network7Service = MyApp.retrofit.create(Network7Service.class);

        simple();
    }


    private void simple(){
//        network7Service.downloadMp3().enqueue(new Callback<ResponseBody>() {
//        network7Service.downloadImg().enqueue(new Callback<ResponseBody>() {
        network7Service.downloadVideo().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Response<ResponseBody> response1 = response;
                MediaType mediaType = response.body().contentType();

//                获取文件类型
//                MediaType mediaType = response.body().contentType();  "audio/mpeg"
//                mediaType.type(); //audio
//                mediaType.subtype(); //mpeg
//
//                音频mp3  "audio/mpeg"
//                图片jpg  "image/jpeg"
//                视频mp4  "video/mp4"

                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        writeFile2Disk(response, new File(Environment.getExternalStorageDirectory(), "音乐.mp3"));
//                        writeFile2Disk(response, new File(Environment.getExternalStorageDirectory(), "图片.jpg"));
                        writeFile2Disk(response, new File(Environment.getExternalStorageDirectory(), "视频.mp4"));
                    }
                }).start();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Throwable t1 = t;
            }
        });
    }

    /**
     * 将文件写入本地
     * @param response 网络请求
     * @param file 保存的文件位置
     */
    private void writeFile2Disk(Response<ResponseBody> response, File file) {
        long currentLength = 0;
        OutputStream os = null;
        InputStream is = response.body().byteStream(); //获取下载输入流
        long totalLength = response.body().contentLength(); //获取下载文件的大小
        try {
            os = new FileOutputStream(file); //输出流
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                Log.e("huangjiepan", "当前进度: " + currentLength + ", 总大小: " + totalLength);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close(); //关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close(); //关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}