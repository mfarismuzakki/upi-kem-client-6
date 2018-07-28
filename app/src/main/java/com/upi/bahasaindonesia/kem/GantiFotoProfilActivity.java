package com.upi.bahasaindonesia.kem;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.upi.bahasaindonesia.kem.globals.Variables;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GantiFotoProfilActivity extends AppCompatActivity {

    private ImageView tampil_foto;
    private int[] gambar = {R.drawable.profil_1, R.drawable.profil_2, R.drawable.profil_3, R.drawable.profil_4, R.drawable.profil_5, R.drawable.profil_6, R.drawable.profil_7, R.drawable.profil_8};
    private String select = "profil_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_foto_profil);

        tampil_foto = findViewById(R.id.tampil_foto);
        ImageButton profil_1 = findViewById(R.id.profil_1);
        ImageButton profil_2 = findViewById(R.id.profil_2);
        ImageButton profil_3 = findViewById(R.id.profil_3);
        ImageButton profil_4 = findViewById(R.id.profil_4);
        ImageButton profil_5 = findViewById(R.id.profil_5);
        ImageButton profil_6 = findViewById(R.id.profil_6);
        ImageButton profil_7 = findViewById(R.id.profil_7);
        ImageButton profil_8 = findViewById(R.id.profil_8);

        int status = 0, i = 1;
        while (status == 0 && i < 9){
            if (BerandaActivity.akun.getFotoProfil().equals("profil_" + Integer.toString(i))){
                tampil_foto.setImageResource(gambar[i -1]);
                status = 1;
            }
            i++;
        }

        profil_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampil_foto.setImageResource(gambar[0]);
                select = "profil_1";
            }
        });

        profil_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampil_foto.setImageResource(gambar[1]);
                select = "profil_2";
            }
        });

        profil_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampil_foto.setImageResource(gambar[2]);
                select = "profil_3";
            }
        });

        profil_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampil_foto.setImageResource(gambar[3]);
                select = "profil_4";
            }
        });

        profil_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampil_foto.setImageResource(gambar[4]);
                select = "profil_5";
            }
        });

        profil_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampil_foto.setImageResource(gambar[5]);
                select = "profil_6";
            }
        });

        profil_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampil_foto.setImageResource(gambar[6]);
                select = "profil_7";
            }
        });

        profil_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampil_foto.setImageResource(gambar[7]);
                select = "profil_8";
            }
        });

        ImageButton save = findViewById(R.id.tombol_simpan);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProsesInputHasil().execute();
            }
        });

        ImageButton kembali = findViewById(R.id.tombol_keluar);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class ProsesInputHasil extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            LinearLayout loadingFoto = findViewById(R.id.loading_foto);
            loadingFoto.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            URL url = null;

            try {
                url = new URL(Variables.API + "Akun/gantifotoprofil");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection httpURLConnection = null;

            try {
                assert url != null;
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                assert httpURLConnection != null;
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            httpURLConnection.addRequestProperty("Accept", "application/json");
            httpURLConnection.addRequestProperty("Content-Type", "application/json");

            try {
                httpURLConnection.connect();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("kode_akun", BerandaActivity.akun.getKode());
                jsonObject.put("nama_gambar", select);

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(jsonObject.toString());
                dataOutputStream.flush();
                dataOutputStream.close();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            try {
                httpURLConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            BerandaActivity.akun.setFotoProfil(select);
            int status = 0, i = 1;
            while (status == 0 && i < 9){
                if (BerandaActivity.akun.getFotoProfil().equals("profil_" + Integer.toString(i))){
                    ProfilFragment.foto.setImageResource(gambar[i -1]);
                    BerandaActivity.foto_profil.setImageResource(gambar[i -1]);
                    status = 1;
                }
                i++;
            }

            finish();
        }
    }
}
