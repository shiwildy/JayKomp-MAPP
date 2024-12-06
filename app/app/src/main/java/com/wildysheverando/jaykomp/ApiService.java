/*

    JayKomp-MAPP
    Merupakan aplikasi android yang dibuat untuk kebutuhan project akhir mata kuliah webmobile programming

    Author: Wildy Sheverando <hai@shiwildy.com>

    File ini merupakan bagian dari
    https://github.com/shiwildy/JayKomp-MAPP

*/

package com.wildysheverando.jaykomp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Query;
import java.util.List;
import java.util.Map;

public interface ApiService {
    // Endpoint API untuk login
    @POST("/login")
    Call<Map<String, String>> login(@Body Map<String, String> credentials);

    // Endpoint API untuk register
    @POST("/register")
    Call<Map<String, String>> register(@Body Map<String, String> credentials);

    // Endpoint API untuk forget password
    @POST("/forget")
    Call<Map<String, String>> forget(@Body Map<String, String> email);

    // Endpoint API untuk ambil product
    @GET("/getnewproduct")
    Call<List<Product>> getNewProducts();

    // Endpoint API untuk mencari produk
    @GET("/searchproduct")
    Call<List<Product>> searchProducts(@Query("keyword") String keyword);
}