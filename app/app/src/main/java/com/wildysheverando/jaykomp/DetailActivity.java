/*

    JayKomp-MAPP
    Merupakan aplikasi android yang dibuat untuk kebutuhan project akhir mata kuliah webmobile programming

    Author: Wildy Sheverando <hai@shiwildy.com>

    File ini merupakan bagian dari
    https://github.com/shiwildy/JayKomp-MAPP

*/

package com.wildysheverando.jaykomp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide; // pake glide untuk load images

public class DetailActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productDescription;
    private Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        productImage = findViewById(R.id.detailImage);
        productName  = findViewById(R.id.detailName);
        productPrice = findViewById(R.id.detailPrice);
        productDescription = findViewById(R.id.detailDescription);
        buyButton = findViewById(R.id.buyButton);
        Product product = (Product) getIntent().getSerializableExtra("product");

        if(product != null){
            productName.setText(product.getNama());
            productPrice.setText("Rp " + product.getHarga());
            productDescription.setText(product.getDeskripsi());

            Glide.with(this)
                    .load(product.getImages())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(productImage);
        }

        // buy button
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "Fitur beli tidak tersedia\n\nDikarnakan project ini hanya untuk kebutuhan UAS, jadi tidak diintegrasikan dengan Payment Gateway.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}