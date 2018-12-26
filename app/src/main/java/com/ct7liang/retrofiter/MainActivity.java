package com.ct7liang.retrofiter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Simple1Activity.class    --->    @POST @GET @PUT DELETE @PATCH @HEAD @OPTIONS @HTTP @Url @Path
 * Simple2Activity.class    --->    @Headers @Header
 * Simple3Activity.class    --->    @Body
 * Simple4Activity.class    --->    @FormUrlEncoded @Field @FieldMap @Query @QueryMap
 * Simple5Activity.class    --->    @Multipart @Part @PartMap
 * Simple6Activity.class    --->    Converter数据转换
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, Simple6Activity.class));
    }
}
