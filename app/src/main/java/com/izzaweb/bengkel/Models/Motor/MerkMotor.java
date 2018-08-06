package com.izzaweb.bengkel.Models.Motor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MerkMotor {
    @SerializedName("id_merk_motor")
    @Expose
    private String idMerkMotor;
    @SerializedName("nama_merk")
    @Expose
    private String namaMerk;

    public String getIdMerkMotor() {
        return idMerkMotor;
    }

    public void setIdMerkMotor(String idMerkMotor) {
        this.idMerkMotor = idMerkMotor;
    }

    public String getNamaMerk() {
        return namaMerk;
    }

    public void setNamaMerk(String namaMerk) {
        this.namaMerk = namaMerk;
    }

}
