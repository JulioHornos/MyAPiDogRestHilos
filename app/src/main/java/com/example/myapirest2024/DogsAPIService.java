package com.example.myapirest2024;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogsAPIService {
        String url = "images";
        @GET(url)
        Call<DogRespuesta> getDogs();

        // defino un parámetro raza en el método GetDogs2 que digo que va a ir al path
        // en el path indico en que lugar se pone la raza
        String url2 = "{raza}/images";
        @GET(url2)
        Call<DogRespuesta> getDogs2(@Path("raza") String raza);
}
