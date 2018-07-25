package com.upi.bahasaindonesia.kem.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Kuis implements Serializable {
    private List<Integer> arrKodeSoal = new ArrayList<>();
    private List<String> arrTeks = new ArrayList<>();
    private List<Integer> arrPoin = new ArrayList<>();
    private List<Integer> nomorUrut = new ArrayList<>();

    public void reset(){
        arrKodeSoal.clear();
        arrTeks.clear();
        arrPoin.clear();
        nomorUrut.clear();
    }

    public List<Integer> getNomor(){
        return nomorUrut;
    }

    public void setNomor(List<Integer> nomorUrut){
        this.nomorUrut = nomorUrut;
    }

    public Integer getKodeSoal(int i){
        return arrKodeSoal.get(i);
    }

    public void setKodeSoal(int kodeSoal){
        arrKodeSoal.add(kodeSoal);
    }

    public String getTeks(int i){
        return arrTeks.get(i);
    }

    public void setTeks(String teks){
        arrTeks.add(teks);
    }

    public Integer getPoin(int i){
        return arrPoin.get(i);
    }

    public void setPoin(int poin){
        arrPoin.add(poin);
    }


    private Integer[][] arrChoiceKodePilihanJawaban = new Integer[100][100];
    private Integer[][] arrChoiceKodeSoal = new Integer[100][100];
    private String[][] arrChoiceTeks = new String[100][100];
    private String[][] arrChoiceStatus = new String[100][100];

    public Integer[] getChoiceKodePilihanJawaban(int i){
        return arrChoiceKodePilihanJawaban[i];
    }

    public void setChoiceKodePilihanJawaban(int kode, int i, int j){
        arrChoiceKodePilihanJawaban[i][j] = kode;
    }

    public Integer[] getChoiceKodeSoal(int i){
        return arrChoiceKodeSoal[i];
    }

    public void setChoiceKodeSoal(int kode, int i, int j){
        arrChoiceKodeSoal[i][j] = kode;
    }

    public String[] getChoiceTeks(int i){
        return arrChoiceTeks[i];
    }

    public void setChoiceTeks(String teks, int i, int j){
        arrChoiceTeks[i][j] = teks;
    }

    public String[] getChoiceStatus(int i){
        return arrChoiceStatus[i];
    }

    public void setChoiceStatus(String status, int i, int j){
        arrChoiceStatus[i][j] = status;
    }


    private int kode_buku_teks;
    private int waktu_baca;
    private int soal_benar;
    private int poin_max;
    private int poin_didapat;
    private int jumlahKata;
    private List<Integer> kode_pilihan_jawaban = new ArrayList<>();

    public Integer getKodeBukuTeks(){
        return kode_buku_teks;
    }

    public void setKodeBukuTeks(Integer kode_buku_teks){
        this.kode_buku_teks = kode_buku_teks;
    }

    public Integer getWaktuBaca(){
        return waktu_baca;
    }

    public void setWaktuBaca(Integer waktu_baca){
        this.waktu_baca = waktu_baca;
    }

    public Integer getSoalBenar(){
        return soal_benar;
    }

    public void setSoalBenar(Integer soal_benar){
        this.soal_benar = soal_benar;
    }

    public List<Integer> getKodePilihanJawaban(){
        return kode_pilihan_jawaban;
    }

    public void setKodePilihanJawaban(Integer kode_pilihan){
        kode_pilihan_jawaban.add(kode_pilihan);
    }

    public int getJumlahKata() {
        return jumlahKata;
    }

    public void setJumlahKata(int jumlahKata) {
        this.jumlahKata = jumlahKata;
    }

    public int getPoinMax() {
        return poin_max;
    }

    public void setPoinMax(int poin_max) {
        this.poin_max = poin_max;
    }

    public int getPoinDidapat() {
        return poin_didapat;
    }

    public void setPoinDidapat(int poin_didapat) {
        this.poin_didapat = poin_didapat;
    }

}
