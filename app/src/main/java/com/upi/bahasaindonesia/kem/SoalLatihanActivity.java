package com.upi.bahasaindonesia.kem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.upi.bahasaindonesia.kem.globals.Variables;
import com.upi.bahasaindonesia.kem.models.Kuis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SoalLatihanActivity extends AppCompatActivity {

    Button tombolGantiSoal;
    TextView soal;
    private Kuis kuis = new Kuis();
    String mJawaban = "";
    private int mNilai = 0;
    private int nilai_max = 0;
    public List<String> allChoice = new ArrayList<>();
    public List<Integer> nomorUrut = new ArrayList<>();
    RadioGroup rg;
    RadioButton rb, rb1, rb2, rb3, rb4;
    String Jaw = "";
    Random r;
    int num = 0;
    int max = 0;
    int nilai = 0;
    int kode_pilihan_jawaban;
    String[] pilihan;
    String[] jawaban;
    Integer[] kode;
    int benar = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal_latihan);

        r = new Random();

        rg = findViewById(R.id.rbgroup);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        tombolGantiSoal = findViewById(R.id.kembali_latihan);
        soal = findViewById(R.id.soal);

        Intent i = getIntent();
        kuis = (Kuis)i.getSerializableExtra("objKuis");

        nomorUrut = kuis.getNomor();
        max = nomorUrut.size();

        updateSoal();

        tombolGantiSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kuis.setKodePilihanJawaban(kode_pilihan_jawaban);
                nilai_max = nilai_max + nilai;
                if (Jaw.equals(mJawaban)) {
                    mNilai = mNilai + nilai;
                    benar++;
                }

                if (num < max) {
                    rg.clearCheck();
                    updateSoal();
                } else {
                    kuis.setPoinDidapat(mNilai);
                    kuis.setPoinMax(nilai_max);
                    kuis.setSoalBenar(benar);
                    
                    new ProsesInputHasil().execute();
                }
            }
        });
    }

    public void rbclick (View v) {
        int radioButton = rg.getCheckedRadioButtonId();
        rb = findViewById(radioButton);
        Jaw = rb.getText().toString();
        for (int i = 0; i < 4; i++) {
            if (Jaw.equals(pilihan[i])) {
                kode_pilihan_jawaban = kode[i];
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateSoal() {
        int nomor = num;
        soal.setText(Integer.toString(nomor + 1) + ". " + kuis.getTeks(nomorUrut.get(num)));

        nilai = kuis.getPoin(nomorUrut.get(num));
        allChoice.clear();
        String kunjaw = "";
        pilihan = kuis.getChoiceTeks(nomorUrut.get(num));
        jawaban = kuis.getChoiceStatus(nomorUrut.get(num));
        kode = kuis.getChoiceKodePilihanJawaban(nomorUrut.get(num));

        for (int i = 0; i < 4; i++){
            allChoice.add(pilihan[i]);
            if (jawaban[i].equals("benar")) {
                kunjaw = pilihan[i];
            }
        }
        Collections.shuffle(allChoice);

        rb1.setText(allChoice.get(0));
        rb2.setText(allChoice.get(1));
        rb3.setText(allChoice.get(2));
        rb4.setText(allChoice.get(3));

        mJawaban = kunjaw;
        num++;
    }

    @SuppressLint("StaticFieldLeak")
    private class ProsesInputHasil extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            URL url = null;

            try {
                url = new URL(Variables.API + "Kuis/update");
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
                jsonObject.put("kode_buku_teks", kuis.getKodeBukuTeks());
                jsonObject.put("waktu_baca", kuis.getWaktuBaca());

                int banyak = kuis.getKodePilihanJawaban().size();
                jsonObject.put("banyak", banyak);

                for (int i = 0; i < kuis.getKodePilihanJawaban().size(); i++){
                    jsonObject.put("kode_pilihan_jawaban_" + i, kuis.getKodePilihanJawaban().get(i));
                }

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

            Intent intent = new Intent(SoalLatihanActivity.this, HasilKuisActivity.class);
            intent.putExtra("objKuis", kuis);
            startActivity(intent);

            finish();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
