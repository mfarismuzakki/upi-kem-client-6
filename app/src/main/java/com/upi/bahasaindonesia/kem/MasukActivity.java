package com.upi.bahasaindonesia.kem;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.upi.bahasaindonesia.kem.globals.Variables;
import com.upi.bahasaindonesia.kem.models.Akun;
import com.upi.bahasaindonesia.kem.models.BukuTeks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class MasukActivity extends AppCompatActivity {

    private EditText masukanNisn;
    private EditText masukanKataSandi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        // Mendapatkan data akun dari masukan pengguna
        masukanNisn = findViewById(R.id.masuk_nisn);
        masukanKataSandi = findViewById(R.id.masuk_kata_sandi);

        // Untuk keperluan pengembangan aplikasi
//        masukanNisn.setText("10117229");
//        masukanKataSandi.setText("pass");

        Button tombolMasuk = findViewById(R.id.tombol_masuk);
        tombolMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!masukanNisn.getText().toString().equals("") && !masukanKataSandi.getText().toString().equals("")) {
                    new ProsesMasuk().execute();

                    InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (inputManager != null) {
                        inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Harap isi semua kotak masukan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class ProsesMasuk extends AsyncTask<Void, Void, Boolean> {

        private ProgressBar bilahKemajuan;

        private String pesan = "";

        private Akun akun;
        private ArrayList<BukuTeks> bukuTeksArrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bilahKemajuan = findViewById(R.id.bilah_kemajuan);

            bilahKemajuan.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            URL url = null;

            try {
                url = new URL(Variables.API + "Akun/login");
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
                jsonObject.put("nisn", masukanNisn.getText().toString());
                jsonObject.put("kata_sandi", masukanKataSandi.getText().toString());

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(jsonObject.toString());
                dataOutputStream.flush();
                dataOutputStream.close();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            try {
                int HttpResponse = httpURLConnection.getResponseCode();

                if (HttpResponse == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                    String line;
                    StringBuilder stringBuilder = new StringBuilder("");

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    pesan = jsonObject.getString("pesan");

                    if (pesan.equals("OK")) {
                        akun = new Akun();
                        akun.setKode(jsonObject.getInt("kode_akun"));
                        akun.setNisn(jsonObject.getString("nisn"));
                        akun.setKataSandi(jsonObject.getString("kata_sandi"));
                        akun.setNamaLengkap(jsonObject.getString("nama_lengkap"));
                        akun.setSekolah(jsonObject.getString("sekolah"));
                        akun.setKelas(jsonObject.getInt("kelas"));
                        akun.setNomorTeksBacaan(jsonObject.getInt("nomor_buku_teks"));

                        try {
                            url = new URL(Variables.API + "Buku_Teks/get_by_class/" + Integer.toString(akun.getKelas()));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        httpURLConnection = null;

                        try {
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {

                            HttpResponse = httpURLConnection.getResponseCode();

                            if (HttpResponse == HttpURLConnection.HTTP_OK) {

                                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                                stringBuilder = new StringBuilder("");
                                while ((line = bufferedReader.readLine()) != null) {
                                    stringBuilder.append(line).append("\n");
                                }
                                bufferedReader.close();

                                JSONArray jsonArray = new JSONArray(stringBuilder.toString());

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    BukuTeks bukuTeks = new BukuTeks();
                                    bukuTeks.setKode(jsonObject.getInt("kode_buku_teks"));
                                    bukuTeks.setJudul(jsonObject.getString("judul"));
                                    bukuTeks.setTeks(jsonObject.getString("teks"));

                                    String trimmed = bukuTeks.getTeks().trim();
                                    bukuTeks.setJumlahKata(trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length);

                                    bukuTeksArrayList.add(bukuTeks);
                                }
                            }

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (IOException | JSONException e) {
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

            if (pesan.equals("OK")) {
                Intent intent = new Intent(getApplicationContext(), BerandaActivity.class);
                intent.putExtra("akun", akun);
                intent.putExtra("bukuteks", bukuTeksArrayList);
                startActivity(intent);

                finish();
            } else {
                new AlertDialog.Builder(MasukActivity.this)
                        .setTitle("Gagal masuk!")
                        .setMessage("Pastikan masukan kamu benar.")
                        .setNegativeButton("Coba lagi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bilahKemajuan.setVisibility(View.GONE);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        }
    }
}
