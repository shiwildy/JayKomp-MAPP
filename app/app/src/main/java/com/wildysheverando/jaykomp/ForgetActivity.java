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
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetActivity extends AppCompatActivity {
    private EditText emailEditText;
    private Button submitButton;
    private TextView kembaliTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        emailEditText = findViewById(R.id.email);
        submitButton = findViewById(R.id.submitbutton);
        kembaliTextview = findViewById(R.id.kembali);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();

                if (isValidEmail(email)) {
                    lupaPassword(email);
                } else {
                    emailEditText.setError("Mohon masukkan email yang valid.");
                }
            }
        });

        kembaliTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void lupaPassword(String email) {
        Map<String, String> emailMap = new HashMap<>();
        emailMap.put("email", email);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Map<String, String>> call = apiService.forget(emailMap);

        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    Map<String, String> responseBody = response.body();
                    if (responseBody != null && responseBody.containsKey("message")) {
//                        String message = responseBody.get("message");
//                        Toast.makeText(ForgetActivity.this, message, Toast.LENGTH_SHORT).show();
                        Toast.makeText(ForgetActivity.this, "Email reset password telah dikirim.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgetActivity.this, "Gagal kirim email reset password.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(ForgetActivity.this, "Terjadi kesalahan jaringan.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}