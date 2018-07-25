package com.upi.bahasaindonesia.kem.models;

import java.io.Serializable;

public class Akun implements Serializable {
    private int kode;
    private String nisn;
    private String kataSandi;
    private String namaLengkap;
    private String sekolah;
    private int kelas;
    private int nomorTeksBacaan;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getKataSandi() {
        return kataSandi;
    }

    public void setKataSandi(String kataSandi) {
        this.kataSandi = kataSandi;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getSekolah() {
        return sekolah;
    }

    public void setSekolah(String sekolah) {
        this.sekolah = sekolah;
    }

    public int getKelas() {
        return kelas;
    }

    public void setKelas(int kelas) {
        this.kelas = kelas;
    }

    public int getNomorTeksBacaan() {
        return nomorTeksBacaan;
    }

    public void setNomorTeksBacaan(int nomorTeksBacaan) {
        this.nomorTeksBacaan = nomorTeksBacaan;
    }
}
