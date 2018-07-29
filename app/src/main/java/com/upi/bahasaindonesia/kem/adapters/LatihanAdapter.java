package com.upi.bahasaindonesia.kem.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.upi.bahasaindonesia.kem.BacaanActivity;
import com.upi.bahasaindonesia.kem.BerandaActivity;
import com.upi.bahasaindonesia.kem.R;
import com.upi.bahasaindonesia.kem.models.BukuTeks;

import java.util.ArrayList;

public class LatihanAdapter extends RecyclerView.Adapter<LatihanAdapter.ViewHolder> {

    private ArrayList<BukuTeks> bukuTeks;
    private Context context;

    public LatihanAdapter(ArrayList<BukuTeks> bukuTeks, Context context) {
        this.bukuTeks = bukuTeks;
        this.context = context;
    }

    private String[] warna = {"#FDBD57", "#E4716E", "#666666", "#18748A", "#18A076"};

    private int[] gambar1 = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.buku_teks, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.setIsRecyclable(false);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Apakah kamu sudah siap membaca?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, BacaanActivity.class);
                                intent.putExtra("bukuteks", bukuTeks.get(viewHolder.getAdapterPosition()));
                                context.startActivity(intent);

                                ((Activity)context).finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });

        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.judul.setText(bukuTeks.get(position).getJudul());
        holder.jumlahKata.setText("Jumlah Kata: " + Integer.toString(bukuTeks.get(position).getJumlahKata()));

        if (position == BerandaActivity.akun.getNomorTeksBacaan() - 1) {
            holder.gembok.setVisibility(View.GONE);
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Bacaan ini terkunci", Toast.LENGTH_SHORT).show();
                }
            });
        }

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor(warna[position % warna.length]));
        gradientDrawable.setCornerRadius(15);
        holder.wadah.setBackground(gradientDrawable);

        // "% gambar1.length" adalah sebagai pengaman saja
        holder.gambar.setImageResource(gambar1[position % gambar1.length]);
    }

    @Override
    public int getItemCount() {
        return (bukuTeks == null) ? 0 : bukuTeks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView judul;
        TextView jumlahKata;
        ConstraintLayout wadah;
        ImageView gembok;
        ImageView gambar;

        ViewHolder(View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.buku_teks_judul);
            jumlahKata = itemView.findViewById(R.id.buku_teks_jumlah_kata);
            wadah = itemView.findViewById(R.id.teks_buku_wadah);
            gembok = itemView.findViewById(R.id.buku_teks_gembok);
            gambar = itemView.findViewById(R.id.buku_teks_gambar);
        }
    }

}
