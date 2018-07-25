package com.upi.bahasaindonesia.kem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.upi.bahasaindonesia.kem.adapters.LatihanAdapter;

public class DaftarLatihanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_latihan);

        RecyclerView wadahDaftarBukuTeks = findViewById(R.id.wadah_daftar_buku_teks);
        wadahDaftarBukuTeks.setLayoutManager(new LinearLayoutManager(this));
        wadahDaftarBukuTeks.setAdapter(new LatihanAdapter(BerandaActivity.bukuTeks, DaftarLatihanActivity.this));

        ImageButton tombolKeluar = findViewById(R.id.tombol_keluar);

        tombolKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
