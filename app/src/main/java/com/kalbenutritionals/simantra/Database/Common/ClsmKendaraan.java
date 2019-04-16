package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class ClsmKendaraan {
    @DatabaseField(generatedId = true)
    private int intId;
    @DatabaseField
    private String jenisKendaraan;
    @DatabaseField
    private String tipeKendaraan;
    @DatabaseField
    private String tahunPembuatan;
    @DatabaseField
    private String noPol;
    @DatabaseField
    private int kmKendaraan;
    @DatabaseField
    private String lokasi;
    @DatabaseField
    private String keterangan;
    @DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date dtInserted;

    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public void setJenisKendaraan(String jenisKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
    }

    public String getTipeKendaraan() {
        return tipeKendaraan;
    }

    public void setTipeKendaraan(String tipeKendaraan) {
        this.tipeKendaraan = tipeKendaraan;
    }

    public String getTahunPembuatan() {
        return tahunPembuatan;
    }

    public void setTahunPembuatan(String tahunPembuatan) {
        this.tahunPembuatan = tahunPembuatan;
    }

    public String getNoPol() {
        return noPol;
    }

    public void setNoPol(String noPol) {
        this.noPol = noPol;
    }

    public int getKmKendaraan() {
        return kmKendaraan;
    }

    public void setKmKendaraan(int kmKendaraan) {
        this.kmKendaraan = kmKendaraan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Date getDtInserted() {
        return dtInserted;
    }

    public void setDtInserted(Date dtInserted) {
        this.dtInserted = dtInserted;
    }
}
