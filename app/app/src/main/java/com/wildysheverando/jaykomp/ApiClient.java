/*

    JayKomp-MAPP
    Merupakan aplikasi android yang dibuat untuk kebutuhan project akhir mata kuliah webmobile programming

    Author: Wildy Sheverando <hai@shiwildy.com>

    File ini merupakan bagian dari
    https://github.com/shiwildy/JayKomp-MAPP

*/

package com.wildysheverando.jaykomp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    private static final String BASE_URL = "https://api-jaykomp.wildy.my.id";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // setting timeout jadi 30 detik untuk mencegah koneksi lelet
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}