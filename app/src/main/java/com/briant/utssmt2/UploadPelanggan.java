package com.briant.utssmt2;

import java.security.PublicKey;

public class UploadPelanggan {
    public String nama,nohp,alamat,email,tanggal,bank,norek,catatan,url;
    public UploadPelanggan(){

    }

    public UploadPelanggan(String nama,String nohp,String alamat,String email,String tanggal,String bank,String norek,String catatan,String url){
        this.nama = nama;
        this.nohp = nohp;
        this.alamat = alamat;
        this.email = email;
        this.tanggal = tanggal;
        this.bank = bank;
        this.norek = norek;
        this.catatan = catatan;
        this.url = url;
    }
    public String getNama() {
        return nama;
    }

    public String getNohp() {
        return nohp;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getEmail() {
        return email;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getBank() {
        return bank;
    }

    public String getNorek() {
        return norek;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getUrl() {
        return url;
    }
}
