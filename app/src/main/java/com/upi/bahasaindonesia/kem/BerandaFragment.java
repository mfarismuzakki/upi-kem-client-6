package com.upi.bahasaindonesia.kem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {


    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_beranda, container, false);

        LinearLayout tombolInfo = v.findViewById(R.id.tombol_informasi);
        LinearLayout tombolPetunjuk = v.findViewById(R.id.tombol_petunjuk);
        LinearLayout tombolLatihan = v.findViewById(R.id.tombol_latihan);
        LinearLayout tombolGrafik = v.findViewById(R.id.tombol_grafik);

        tombolInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InformasiActivity.class));
            }
        });
        tombolPetunjuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PetunjukActivity.class));
            }
        });
        tombolLatihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DaftarLatihanActivity.class));
            }
        });
        tombolGrafik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GrafikActivity.class));
            }
        });

        return v;
    }



}
