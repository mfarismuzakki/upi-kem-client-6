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

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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

            CombinedChart grafik = findViewById(R.id.grafik);

            ArrayList<BarEntry> entries = new ArrayList<>();

            entries.add(new BarEntry(0, 0));
            for (int i = 1; i < 11; i++) {
                if (i - 1 < skors.size()) {
                    entries.add(new BarEntry(i, skors.get(i - 1).intValue()));
                } else {
                    entries.add(new BarEntry(i, 0));
                }
            }
            entries.add(new BarEntry(11, 0));

            ArrayList<Entry> entries1 = new ArrayList<>();

            for (int i = 0; i < 12; i++) {
                entries1.add(new BarEntry(i, 140));
            }

            final ArrayList<String> labels = new ArrayList<>();
            labels.add("");
            labels.add("Teks 1");
            labels.add("Teks 2");
            labels.add("Teks 3");
            labels.add("Teks 4");
            labels.add("Teks 5");
            labels.add("Teks 6");
            labels.add("Teks 7");
            labels.add("Teks 8");
            labels.add("Teks 9");
            labels.add("Teks 10");
            labels.add("");

            grafik.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));

            grafik.getXAxis().setGranularity(1);
            grafik.getXAxis().setGranularityEnabled(true);

            grafik.setDrawGridBackground(false);

            grafik.getDescription().setEnabled(false);

            grafik.setDrawOrder(new CombinedChart.DrawOrder[]{
                    CombinedChart.DrawOrder.BAR,  CombinedChart.DrawOrder.LINE
            });

            BarDataSet barDataSet = new BarDataSet(entries, "KPM");
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);

            BarData barData = new BarData(barDataSet);

            LineDataSet lineDataSet = new LineDataSet(entries1, "Ideal");
            lineDataSet.setColor(Color.RED);

            LineData lineData = new LineData(lineDataSet);

            CombinedData combinedData = new CombinedData();
            combinedData.setData(barData);
            combinedData.setData(lineData);

            grafik.setData(combinedData);
            grafik.invalidate();

            YAxis rightAxis = grafik.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            YAxis leftAxis = grafik.getAxisLeft();
            leftAxis.setDrawGridLines(false);
            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            XAxis xAxis = grafik.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setAxisMinimum(0f);
            xAxis.setGranularity(1f);

            LinearLayout loadingGrafik = findViewById(R.id.loading_grafik);
            loadingGrafik.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
