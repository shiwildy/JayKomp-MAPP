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

public class CartActivity extends AppCompatActivity {
    private ImageButton berandaButton, pesanButton, profilButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        berandaButton = findViewById(R.id.beranda);
        pesanButton = findViewById(R.id.pesan);
        profilButton = findViewById(R.id.profile);

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        berandaButton.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, BerandaActivity.class);

            // Tangkap nama email dan lempar lagi 🗿
            String nama = getIntent().getStringExtra("nama");
            String email = getIntent().getStringExtra("email");
            intent.putExtra("nama", nama);
            intent.putExtra("email", email);

            startActivity(intent);
        });

        profilButton.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, ProfileActivity.class);

            // Tangkap nama email dan lempar lagi 🗿
            String nama = getIntent().getStringExtra("nama");
            String email = getIntent().getStringExtra("email");
            intent.putExtra("nama", nama);
            intent.putExtra("email", email);

            startActivity(intent);
        });

        pesanButton.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, PesanActivity.class);

            // Tangkap nama email dan lempar lagi 🗿
            String nama = getIntent().getStringExtra("nama");
            String email = getIntent().getStringExtra("email");
            intent.putExtra("nama", nama);
            intent.putExtra("email", email);

            startActivity(intent);
        });
    }
}