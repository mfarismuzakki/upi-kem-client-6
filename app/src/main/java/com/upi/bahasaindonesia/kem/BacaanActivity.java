package com.upi.bahasaindonesia.kem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.upi.bahasaindonesia.kem.globals.Variables;
import com.upi.bahasaindonesia.kem.models.BukuTeks;
import com.upi.bahasaindonesia.kem.models.Kuis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BacaanActivity extends AppCompatActivity {

    private List<Integer> allQuestion = new ArrayList<>();
    public Kuis kuis = new Kuis();
    private BukuTeks bukuTeks;
    public int tampKode = 0, j = 0, k = 0, l = 0, status = 0;

    TextView stopwatch;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;

    int Seconds, Minutes;
    int waktu = 0;

    Handler handler;

    TextView judul;
    TextView teks;

    Button tombolSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacaan);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        bukuTeks = (BukuTeks) getIntent().getSerializableExtra("bukuteks");

        judul = findViewById(R.id.teks_buku_judul);
        teks = findViewById(R.id.teks_buku_teks);
        tombolSelesai = findViewById(R.id.tombol_selesai_membaca);
        stopwatch = findViewById(R.id.stopwatch);

        tombolSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == 1) {
                    kuis.setKodeBukuTeks(bukuTeks.getKode());
                    kuis.setWaktuBaca(waktu);

                    Intent intent = new Intent(BacaanActivity.this, SoalLatihanActivity.class);
                    intent.putExtra("objKuis", kuis);
                    intent.putExtra("bukuteks", bukuTeks);
                    startActivity(intent);

                    finish();
                }
            }
        });

        new GetQuestion().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetQuestion extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL(Variables.API + "Kuis/get_by_id/" + Integer.toString(bukuTeks.getKode()));
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
                httpURLConnection.setRequestMethod("GET");
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

                int HttpResponse = httpURLConnection.getResponseCode();

                if (HttpResponse == HttpURLConnection.HTTP_OK) {

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                    String line;
                    StringBuilder stringBuilder = new StringBuilder("");
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();

                    JSONArray jsonArray = new JSONArray(stringBuilder.toString());

                    allQuestion.clear();
                    kuis.reset();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject= jsonArray.getJSONObject(i);

                        if (i == 0){
                            tampKode = jsonObject.getInt("kode_soal_asal");
                            kuis.setKodeSoal(jsonObject.getInt("kode_soal_asal"));
                            kuis.setTeks(jsonObject.getString("teks_soal"));
                            kuis.setPoin(jsonObject.getInt("poin"));
                            allQuestion.add(j);
                            j++;
                            k = 0;
                        }

                        else if (tampKode != jsonObject.getInt("kode_soal_asal")) {
                            tampKode = jsonObject.getInt("kode_soal_asal");
                            kuis.setKodeSoal(jsonObject.getInt("kode_soal_asal"));
                            kuis.setKodeBukuTeks(jsonObject.getInt("kode_buku_teks"));
                            kuis.setTeks(jsonObject.getString("teks_soal"));
                            kuis.setPoin(jsonObject.getInt("poin"));
                            allQuestion.add(j);
                            j++;
                            l++;
                            k = 0;
                        }

                        kuis.setChoiceKodePilihanJawaban(jsonObject.getInt("kode_pilihan_jawaban"), l, k);
                        kuis.setChoiceKodeSoal(jsonObject.getInt("kode_soal"), l, k);
                        kuis.setChoiceTeks(jsonObject.getString("teks_pilihan"), l, k);
                        kuis.setChoiceStatus(jsonObject.getString("status"), l, k);
                        k++;
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
        protected void onPostExecute(final Boolean success) {
            super.onPostExecute(success);

            status = 1;
            Collections.shuffle(allQuestion);
            kuis.setNomor(allQuestion);

            LinearLayout loadingBacaan = findViewById(R.id.loading_bacaan);
            loadingBacaan.setVisibility(View.GONE);

            judul.setText(bukuTeks.getJudul());
            teks.setText(bukuTeks.getTeks());

            String trimmed = bukuTeks.getTeks().trim();
            kuis.setJumlahKata(trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length);

            handler = new Handler();

            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);

            tombolSelesai.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    @Override
    public void onBackPressed() {
    }

    public Runnable runnable = new Runnable() {

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            waktu = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            stopwatch.setText("" + Minutes + ":" + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }

    };
}
