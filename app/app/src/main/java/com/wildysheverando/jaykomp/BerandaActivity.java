/*

    JayKomp-MAPP
    Merupakan aplikasi android yang dibuat untuk kebutuhan project akhir mata kuliah webmobile programming

    Author: Wildy Sheverando <hai@shiwildy.com>

    File ini merupakan bagian dari
    https://github.com/shiwildy/JayKomp-MAPP

*/

package com.wildysheverando.jaykomp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ApiService apiService;
    private ImageButton searchButton;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        recyclerView = findViewById(R.id.recyclerView);
        int numberOfColumns = 2; // pake 2 kolom biar kek sopiiii
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        // Konek api dan panggil productadapter
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        adapter = new ProductAdapter(this, new java.util.ArrayList<Product>());
        recyclerView.setAdapter(adapter);

        searchEditText = findViewById(R.id.searchkey);
        searchButton = findViewById(R.id.searchbutton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = searchEditText.getText().toString().trim();
                if (!keyword.isEmpty()) {
                    performSearch(keyword);
                } else {
                    Toast.makeText(BerandaActivity.this, "Masukan keyword yang benar !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fetchProducts();
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        ImageButton pesanButton = findViewById(R.id.pesan);
        ImageButton cartButton = findViewById(R.id.cart);
        ImageButton profileButton = findViewById(R.id.profile);

        pesanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this, PesanActivity.class);
                startActivity(intent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchProducts() {
        Call<List<Product>> call = apiService.getNewProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Product> products = response.body();
                    adapter.setProductList(products);
                } else {
                    Toast.makeText(BerandaActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(BerandaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performSearch(String keyword) {
        Call<List<Product>> call = apiService.searchProducts(keyword);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Product> searchResults = response.body();
                    if(!searchResults.isEmpty()){
                        adapter.setProductList(searchResults);
                        Toast.makeText(BerandaActivity.this, "Ditemukan " + searchResults.size() + " produk", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setProductList(new java.util.ArrayList<Product>()); // reset product list
                        Toast.makeText(BerandaActivity.this, "Tidak ada produk yang ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BerandaActivity.this, "Terjadi kesalahan pada server.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(BerandaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}