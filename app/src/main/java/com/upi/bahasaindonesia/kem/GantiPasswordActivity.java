package com.upi.bahasaindonesia.kem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.upi.bahasaindonesia.kem.globals.Variables;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GantiPasswordActivity extends AppCompatActivity {

    private EditText kata_sandi, kata_sandi_ulang;
    private Button ganti, kembali;
    private String kataSandi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);

        kata_sandi = findViewById(R.id.masuk_kata_sandi);
        kata_sandi_ulang = findViewById(R.id.masuk_kata_sandi_ulang);
        ganti = findViewById(R.id.ganti);
        kembali = findViewById(R.id.kembali);

        ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kata_sandi.getText().toString().equals(kata_sandi_ulang.getText().toString())){
                    kataSandi = kata_sandi.getText().toString();
                    new ProsesGantiPass().execute();
                }
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class ProsesGantiPass extends AsyncTask<Void, Void, Boolean> {

        private String pesan = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            URL url = null;

            try {
                url = new URL(Variables.API + "Akun/gantipassword");
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
                jsonObject.put("password", kataSandi);

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

            finish();
        }
    }
}
