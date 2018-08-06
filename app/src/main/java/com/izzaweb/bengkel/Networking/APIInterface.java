package com.izzaweb.bengkel.Networking;

import com.izzaweb.bengkel.Models.Bengkel.BengkelList;
import com.izzaweb.bengkel.Models.Bengkel.ResponseBengkel;
import com.izzaweb.bengkel.Models.Booking.BookingList;
import com.izzaweb.bengkel.Models.Booking.UserBooking;
import com.izzaweb.bengkel.Models.Login.LoginRequest;
import com.izzaweb.bengkel.Models.Login.RequestResponse;
import com.izzaweb.bengkel.Models.Motor.ListMotor;
import com.izzaweb.bengkel.Models.User.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    @POST("/api/login")
    Call<RequestResponse> getLoginStatus(@Body LoginRequest request);

    @POST("/api/register")
    Call<RequestResponse> registerUser(@Body User user);

    @POST("/api/edituser")
    Call<RequestResponse> editUser(@Body User user);

    @GET("/api/user/{id_user}")
    Call<User> getUserData(@Path("id_user") int id_user);

    @GET("/api/bookingdata/{id_user}")
    Call<BookingList> getBookingData(@Path("id_user") int id_user);

    @GET("/api/allbengkel")
    Call<BengkelList> getAllBengkel();

    @GET("/api/allbengkel")
    Call<ResponseBengkel> getBengkelAll();

    @GET("/api/allmotor")
    Call<ListMotor> getAllMotor();

    @POST("/api/booking")
    Call<RequestResponse> bookingInput(@Body UserBooking booking);

}
