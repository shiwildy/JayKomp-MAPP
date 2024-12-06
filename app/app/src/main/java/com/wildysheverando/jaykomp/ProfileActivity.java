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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profileImageView;
    private TextView profileNameTextView, profileEmailTextView;
    private Button logoutButton;
    private ImageButton berandaButton, pesanButton, cartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logoutButton = findViewById(R.id.logoutButton);
        berandaButton = findViewById(R.id.beranda);
        pesanButton = findViewById(R.id.pesan);
        cartButton = findViewById(R.id.cart);

        // direct logout button ke function logout
        logoutButton.setOnClickListener(view -> logout());

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        berandaButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, BerandaActivity.class);
            startActivity(intent);
        });

        pesanButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, PesanActivity.class);
            startActivity(intent);
        });

        cartButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void logout() {
        Toast.makeText(ProfileActivity.this, "Berhasil logout", Toast.LENGTH_SHORT).show();

        // Lempar balik ke main
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
