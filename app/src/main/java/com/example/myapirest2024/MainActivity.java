package com.example.myapirest2024;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String raza;
    LinearLayoutManager myLayout;
    RecyclerView myRecicler;
    MiAdaptador Myadapter;
    EditText edt1;

    DogRespuesta dogRespuesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt1 = findViewById(R.id.edt2);

        myLayout=new LinearLayoutManager(this);
        myRecicler=(RecyclerView) findViewById(R.id.rvPerros);
        myRecicler.setLayoutManager(myLayout);
    }

    public void buscarFotos(View vista){
        raza = edt1.getText().toString();
        if (raza.isEmpty()){
            Toast.makeText(this, "Debe seleccionar una raza", Toast.LENGTH_SHORT).show();
            return;
        }
        HiloGetDogs myHilo = new HiloGetDogs(this,raza);
        myHilo.start();
    }

    public void cargaFotos(View vista){

        raza = edt1.getText().toString();
        if (raza.isEmpty()){
            Toast.makeText(this, "Debe seleccionar una raza", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                String cadena = "https://dog.ceo/api/breed/"+raza+"/";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(cadena)
                        .addConverterFactory(GsonConverterFactory.create(
                                new GsonBuilder().serializeNulls().create()
                        ))
                        .build();

                DogsAPIService dogApiService = retrofit.create(DogsAPIService.class);
                Call<DogRespuesta> call = dogApiService.getDogs();

                try {
                    dogRespuesta = call.execute().body();

                    Log.d("Carga", "Cargado dogRespuesta");

                } catch (IOException e) {
                    Log.d("Error", "Error  cargando dogRespuesta");
                }
                catch( RuntimeException r ) {
                    Log.d("Error", "Error  cargando dogRespuesta");
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (dogRespuesta==null) {
                            Toast.makeText(getApplicationContext(), "No se han encontrado fotos de esa raza", Toast.LENGTH_SHORT).show();
                        }else{
                            Myadapter = new MiAdaptador(MainActivity.this,dogRespuesta);
                            myRecicler.setAdapter(Myadapter);
                        }
                    }
                });
            }
        }).start();
    }

    public void pintarRecycler(DogRespuesta myDogRespuesta){
        if (myDogRespuesta==null) {
            Toast.makeText(getApplicationContext(), "No se han encontrado fotos de esa raza", Toast.LENGTH_SHORT).show();
        }else{
            Myadapter = new MiAdaptador(MainActivity.this,myDogRespuesta);
            myRecicler.setAdapter(Myadapter);
        }
    }
}