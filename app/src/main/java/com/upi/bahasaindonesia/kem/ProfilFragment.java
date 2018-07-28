package com.upi.bahasaindonesia.kem;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.upi.bahasaindonesia.kem.globals.Variables;

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
import java.util.Objects;

public class ProfilFragment extends Fragment {

    TextView restart, nama, sekolah, kelas, nisn, ganti_password, ganti_foto_profil;
    public static ImageView foto;
    private int[] gambar = {R.drawable.profil_1, R.drawable.profil_2, R.drawable.profil_3, R.drawable.profil_4, R.drawable.profil_5, R.drawable.profil_6, R.drawable.profil_7, R.drawable.profil_8};

    public ProfilFragment() {
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profil, container, false);

        restart = v.findViewById(R.id.reset);
        if (BerandaActivity.akun.getNomorTeksBacaan() == 11) {
            restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                            .setMessage("Apakah kamu yakin ingin mereset pencapaianmu?")
                            .setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new ProsesReset().execute();
                                }
                            })
                            .setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            });
        } else {
            restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                            .setMessage("Kamu tidak bisa mereset pencapaianmu karena belum menyelesaikan semua bacaan.")
                            .setPositiveButton("Oke, saya paham", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            });
        }

        ganti_password = v.findViewById(R.id.ganti_password);
        ganti_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GantiPasswordActivity.class));
            }
        });

        ganti_foto_profil = v.findViewById(R.id.ganti_foto_profil);
        ganti_foto_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GantiFotoProfilActivity.class));
            }
        });

        nisn = v.findViewById(R.id.nisn);
        nisn.setText(BerandaActivity.akun.getNisn());

        nama = v.findViewById(R.id.nama_lengkap);
        nama.setText(BerandaActivity.akun.getNamaLengkap());

        sekolah = v.findViewById(R.id.sekolah);
        sekolah.setText(BerandaActivity.akun.getSekolah());

        kelas = v.findViewById(R.id.kelas);
        kelas.setText(Integer.toString(BerandaActivity.akun.getKelas()));

        nisn = v.findViewById(R.id.nisn);
        nisn.setText(BerandaActivity.akun.getNisn());

        foto = v.findViewById(R.id.foto);
        int status = 0, i = 1;
        while (status == 0 && i < 9){
            if (BerandaActivity.akun.getFotoProfil().equals("profil_" + Integer.toString(i))){
                int index = i;
                foto.setImageResource(gambar[index-1]);
                status = 1;
            }
            i++;
        }

        return v;
    }

    @SuppressLint("StaticFieldLeak")
    private class ProsesReset extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            URL url = null;

            try {
                url = new URL(Variables.API + "Kuis/reset");
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

            startActivity(new Intent(getContext(), MasukActivity.class));

            Objects.requireNonNull(getActivity()).finish();

        }
    }

}
