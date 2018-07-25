package com.upi.bahasaindonesia.kem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GantiFotoProfilActivity extends AppCompatActivity {

    private ImageView tampil_foto;
    private ImageButton profil_1, profil_2, profil_3, profil_4, profil_5, profil_6, profil_7, profil_8;
    private URL url_profil_1, url_profil_2, url_profil_3, url_profil_4, url_profil_5, url_profil_6, url_profil_7, url_profil_8;
    private Bitmap bitmap_profil_1, bitmap_profil_2, bitmap_profil_3, bitmap_profil_4, bitmap_profil_5, bitmap_profil_6, bitmap_profil_7, bitmap_profil_8;
    private ImageButton save, kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_foto_profil);

        tampil_foto = findViewById(R.id.tampil_foto);
        profil_1 = findViewById(R.id.profil_1);
        profil_2 = findViewById(R.id.profil_2);
        profil_3 = findViewById(R.id.profil_3);
        profil_4 = findViewById(R.id.profil_4);
        profil_5 = findViewById(R.id.profil_5);
        profil_6 = findViewById(R.id.profil_6);
        profil_7 = findViewById(R.id.profil_7);
        profil_8 = findViewById(R.id.profil_8);

        /*try {
            url_profil_1 = new URL("http://10.0.2.2:80/upi-kem-server-2/assets/profil/profil_1.png");
            bitmap_profil_1 = BitmapFactory.decodeStream(url_profil_1.openConnection().getInputStream());
            profil_1.setImageBitmap(bitmap_profil_1);

            url_profil_2 = new URL("http://10.0.2.2:80/upi-kem-server-2/assets/profil/profil_2.png");
            bitmap_profil_2 = BitmapFactory.decodeStream(url_profil_2.openConnection().getInputStream());
            profil_2.setImageBitmap(bitmap_profil_2);

            url_profil_3 = new URL("http://10.0.2.2:80/upi-kem-server-2/assets/profil/profil_3.png");
            bitmap_profil_3 = BitmapFactory.decodeStream(url_profil_3.openConnection().getInputStream());
            profil_3.setImageBitmap(bitmap_profil_3);

            url_profil_4 = new URL("http://10.0.2.2:80/upi-kem-server-2/assets/profil/profil_4.png");
            bitmap_profil_4 = BitmapFactory.decodeStream(url_profil_4.openConnection().getInputStream());
            profil_4.setImageBitmap(bitmap_profil_4);

            url_profil_5 = new URL("http://10.0.2.2:80/upi-kem-server-2/assets/profil/profil_5.png");
            bitmap_profil_5 = BitmapFactory.decodeStream(url_profil_5.openConnection().getInputStream());
            profil_5.setImageBitmap(bitmap_profil_5);

            url_profil_6 = new URL("http://10.0.2.2:80/upi-kem-server-2/assets/profil/profil_6.png");
            bitmap_profil_6 = BitmapFactory.decodeStream(url_profil_6.openConnection().getInputStream());
            profil_6.setImageBitmap(bitmap_profil_6);

            url_profil_7 = new URL("http://10.0.2.2:80/upi-kem-server-2/assets/profil/profil_7.png");
            bitmap_profil_7 = BitmapFactory.decodeStream(url_profil_7.openConnection().getInputStream());
            profil_7.setImageBitmap(bitmap_profil_7);

            url_profil_8 = new URL("http://10.0.2.2:80/upi-kem-server-2/assets/profil/profil_8.png");
            bitmap_profil_8 = BitmapFactory.decodeStream(url_profil_8.openConnection().getInputStream());
            profil_8.setImageBitmap(bitmap_profil_8);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        save = findViewById(R.id.tombol_simpan);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        kembali = findViewById(R.id.tombol_keluar);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
