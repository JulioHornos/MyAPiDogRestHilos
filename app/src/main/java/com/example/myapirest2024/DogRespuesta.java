package com.example.myapirest2024;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DogRespuesta {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private List<String> images;

    public String getStatus(){
        return status;
    }

    public String getImage(Integer indice){
        return images.get(indice);
    }

    public Integer getCountImage(){
        return images.size();
    }
}
