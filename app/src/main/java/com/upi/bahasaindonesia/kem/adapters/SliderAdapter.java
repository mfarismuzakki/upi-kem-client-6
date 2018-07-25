package com.upi.bahasaindonesia.kem.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.upi.bahasaindonesia.kem.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //daftar gambar
    public int[] slide_image = {
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3,
            R.drawable.s4,

    };

    //daftar header
    public String[] slide_heading = {
            "KEMAMPUAN EFEKTIF MEMBACA",
            "LATIHAN",
            "GRAFIK",
            "SELAMAT DATANG"
    };

    //daftar deskripsi
    public String[] slide_descs = {
            "Aplikasi ini akan membantumu berlatih meningkatkan kemampuan efektif membaca. Secara mandiri, kapan pun, dan di mana pun.",
            "Aplikasi ini dilengkapi dengan sejumlah teks dan soal untuk melatih dan melihat kemampuan membacamu.",
            "Melalui grafik, kamu dapat melihat perkembangan kemampuan membacamu dari setiap teks.",
            "Kenali duniamu! Melalui membaca kamu dapat mengetahui beragam informasi mengenai pengetahuan dan lingkungan sekitarmu. Mari berlatih!"
    };



    @Override
    public int getCount(){
        return slide_descs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o){
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false
        );

        ImageView slideImage = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideDesc = (TextView) view.findViewById(R.id.slide_desc);

        slideImage.setImageResource(slide_image[position]);
        slideDesc.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout)object);
    }


}
