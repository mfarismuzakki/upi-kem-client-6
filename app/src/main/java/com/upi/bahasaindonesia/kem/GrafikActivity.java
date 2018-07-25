package com.upi.bahasaindonesia.kem;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.upi.bahasaindonesia.kem.globals.Variables;

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
import java.text.NumberFormat;
import java.util.ArrayList;

public class GrafikActivity extends AppCompatActivity {

    ArrayList<Double> skors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        ImageButton tombolKeluar = findViewById(R.id.tombol_keluar);

        tombolKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new GetReport().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetReport extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL(Variables.API + "Kuis/report/" + Integer.toString(BerandaActivity.akun.getKode()));
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

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int a = jsonObject.getInt("jumlah_kata");
                        int b = jsonObject.getInt("waktu_baca");
                        int c = jsonObject.getInt("poin_pendapatan_kuis");
                        int d = jsonObject.getInt("poin_maksimal_kuis");

                        skors.add((double) a / b * 60 * c / d);
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

            GraphView graphView = findViewById(R.id.grafik);

            DataPoint[] dataPoints = new DataPoint[skors.size()];
            for (int i = 0; i < skors.size(); i++) {
                dataPoints[i] = new DataPoint(i, skors.get(i));
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

            DataPoint[] dataPoints1 = new DataPoint[skors.size()];
            for (int i = 0; i < skors.size(); i++) {
                dataPoints1[i] = new DataPoint(i, 150);
            }
            LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(dataPoints1);

            series1.setColor(Color.GREEN);

            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);

            graphView.getViewport().setMinX(1);

            graphView.addSeries(series);
            graphView.addSeries(series1);

            LinearLayout loadingGrafik = findViewById(R.id.loading_grafik);
            loadingGrafik.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
