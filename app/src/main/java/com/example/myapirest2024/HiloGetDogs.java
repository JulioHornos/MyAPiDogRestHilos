package com.example.myapirest2024;

import android.util.Log;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HiloGetDogs extends Thread{
    MainActivity myMainActivity;
    String myRaza;
    DogRespuesta dogRespuesta;
    public HiloGetDogs (MainActivity myMainActivity, String raza){
        this.myMainActivity =myMainActivity;
        this.myRaza = raza;
    }

    public void run(){
        String cadena = "https://dog.ceo/api/breed/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(cadena)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()
                ))
                .build();
        DogsAPIService dogApiService = retrofit.create(DogsAPIService.class);
        Call<DogRespuesta> call = dogApiService.getDogs2(myRaza);
        try {
            dogRespuesta = call.execute().body();
        } catch (IOException e) {
            Log.d("Error", "Error  cargando dogRespuesta");
        }
        catch( RuntimeException r ) {
            Log.d("Error", "Error  cargando dogRespuesta");
        }
        myMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myMainActivity.pintarRecycler(dogRespuesta);
            }
        });
    }

}
