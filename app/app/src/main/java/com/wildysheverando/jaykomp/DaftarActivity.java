/*

    JayKomp-MAPP
    Merupakan aplikasi android yang dibuat untuk kebutuhan project akhir mata kuliah webmobile programming

    Author: Wildy Sheverando <hai@shiwildy.com>

    File ini merupakan bagian dari
    https://github.com/shiwildy/JayKomp-MAPP

*/

package com.wildysheverando.jaykomp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Map;
import java.util.HashMap;

public class DaftarActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText namaEditText;
    private EditText passwordEditText;
    private Button daftarButton;
    private TextView kembaliTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        emailEditText = findViewById(R.id.email);
        namaEditText = findViewById(R.id.nama);
        passwordEditText = findViewById(R.id.password);
        daftarButton = findViewById(R.id.daftarbutton);
        kembaliTextview = findViewById(R.id.kembali);
        daftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String nama = namaEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (isValidEmail(email)) {
                    if (password.isEmpty()) {
                        passwordEditText.setError("Password tidak boleh kosong.");
                    } else if (nama.isEmpty()) {
                        namaEditText.setError("Nama tidak boleh kosong.");
                    }

                    // Lempar ke function daftar
                    daftar(nama, email, password);
                } else {
                    emailEditText.setError("Mohon gunakan email yang valid.");
                }
            }
        });

        kembaliTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void daftar(String nama, String email, String password) {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("nama", nama);
        credentials.put("email", email);
        credentials.put("password", password);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Map<String, String>> call = apiService.register(credentials);

        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    Map<String, String> responseBody = response.body();
                    if (responseBody != null && responseBody.containsKey("message")) {
                        Toast.makeText(DaftarActivity.this, "Akun telah berhasil terdaftar.", Toast.LENGTH_SHORT).show();

                        // lempar ke beranda / dashboard
                        Intent intent = new Intent(DaftarActivity.this, BerandaActivity.class);
                        intent.putExtra("nama", nama);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(DaftarActivity.this, "Akun telah terdaftar, Silakan login.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(DaftarActivity.this, "Terjadi kesalahan koneksi.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}