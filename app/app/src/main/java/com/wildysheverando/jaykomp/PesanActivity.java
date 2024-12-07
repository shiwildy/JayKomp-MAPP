/*

    JayKomp-MAPP
    Merupakan aplikasi android yang dibuat untuk kebutuhan project akhir mata kuliah webmobile programming

    Author: Wildy Sheverando <hai@shiwildy.com>

    File ini merupakan bagian dari
    https://github.com/shiwildy/JayKomp-MAPP

*/

package com.wildysheverando.jaykomp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class PesanActivity extends AppCompatActivity {
    private ImageButton berandaButton, cartButton, profilButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        berandaButton = findViewById(R.id.beranda);
        cartButton = findViewById(R.id.cart);
        profilButton = findViewById(R.id.profile);

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        berandaButton.setOnClickListener(view -> {
            Intent intent = new Intent(PesanActivity.this, BerandaActivity.class);

            // Tangkap nama email dan lempar lagi 🗿
            String nama = getIntent().getStringExtra("nama");
            String email = getIntent().getStringExtra("email");
            intent.putExtra("nama", nama);
            intent.putExtra("email", email);

            startActivity(intent);
        });

        profilButton.setOnClickListener(view -> {
            Intent intent = new Intent(PesanActivity.this, ProfileActivity.class);

            // Tangkap nama email dan lempar lagi 🗿
            String nama = getIntent().getStringExtra("nama");
            String email = getIntent().getStringExtra("email");
            intent.putExtra("nama", nama);
            intent.putExtra("email", email);

            startActivity(intent);
        });

        cartButton.setOnClickListener(view -> {
            Intent intent = new Intent(PesanActivity.this, CartActivity.class);

            // Tangkap nama email dan lempar lagi 🗿
            String nama = getIntent().getStringExtra("nama");
            String email = getIntent().getStringExtra("email");
            intent.putExtra("nama", nama);
            intent.putExtra("email", email);

            startActivity(intent);
        });
    }
}
