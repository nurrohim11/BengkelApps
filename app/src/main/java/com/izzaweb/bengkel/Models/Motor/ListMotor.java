package com.izzaweb.bengkel.Models.Motor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ListMotor {
    @SerializedName("motorlist")
    @Expose
    private List<Motor> motorlist = null;

    public List<Motor> getMotorlist() {
        return motorlist;
    }

    public void setMotorlist(List<Motor> motorlist) {
        this.motorlist = motorlist;
    }
}
