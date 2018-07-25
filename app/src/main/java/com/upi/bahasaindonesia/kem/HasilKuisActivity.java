package com.upi.bahasaindonesia.kem;

import android.content.Context;
import android.content.DialogInterface;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.upi.bahasaindonesia.kem.models.BukuTeks;
import com.upi.bahasaindonesia.kem.models.Kuis;

public class HasilKuisActivity extends AppCompatActivity {

    private Kuis kuis = new Kuis();
    private BukuTeks bukuTeks;
    TextView jumlah_soal_benar, waktu_baca, skor_kpm, pesan;
    RatingBar rating;
    Button kembaliLatihan, kembaliBeranda;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_kuis);

        Intent i = getIntent();
        kuis = (Kuis)i.getSerializableExtra("objKuis");
        bukuTeks = (BukuTeks) i.getSerializableExtra("bukuteks");
        Kuis kuis = (Kuis) i.getSerializableExtra("objKuis");

        waktu_baca = findViewById(R.id.waktu_baca);
        waktu_baca.setText(": " + kuis.getWaktuBaca() + " detik");

        jumlah_soal_benar = findViewById(R.id.jumlah_soal_benar);
        jumlah_soal_benar.setText(": " + kuis.getSoalBenar());

        int jumlah_kata = kuis.getJumlahKata();
        int waktu_baca = kuis.getWaktuBaca();
        int poin_didapat = kuis.getPoinDidapat();
        int poin_max = kuis.getPoinMax();
        int kpm = (jumlah_kata * 60 * poin_didapat) / (poin_max * waktu_baca);

        skor_kpm = findViewById(R.id.skor_kpm);
        skor_kpm.setText(kpm + " kpm");

        rating = findViewById(R.id.ratingBar);
        pesan = findViewById(R.id.pesan);

        if (kpm == 0){
            rating.setRating(0);
            pesan.setText("Belajar lebih giat lagi, ya!");
        }
        else if (kpm < 60) {
            rating.setRating(1);
            pesan.setText("Belajar lebih giat lagi, ya!");
        }
        else if (kpm < 80){
            rating.setRating(2);
            pesan.setText("Lumayan, tapi perlu perbaikan!");
        }
        else if (kpm < 100){
            rating.setRating(3);
            pesan.setText("Bagus, cukup membanggakan!");
        }
        else if (kpm < 121){
            rating.setRating(4);
            pesan.setText("Selamat! Pertahankan ya!");
        }
        else {
            rating.setRating(5);
            pesan.setText("Hebat! Kamu sungguh luar biasa!");
        }

        kembaliLatihan = findViewById(R.id.kembali_latihan);
        kembaliLatihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HasilKuisActivity.this, BerandaActivity.class);
                intent.putExtra("akun", BerandaActivity.akun);
                intent.putExtra("bukuteks", BerandaActivity.bukuTeks);
                startActivity(intent);

                BerandaActivity.akun.setNomorTeksBacaan((BerandaActivity.akun.getNomorTeksBacaan() + 1));

                startActivity(new Intent(getApplicationContext(), DaftarLatihanActivity.class));

                finish();
            }
        });

        kembaliBeranda = findViewById(R.id.kembali_beranda);
        kembaliBeranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
