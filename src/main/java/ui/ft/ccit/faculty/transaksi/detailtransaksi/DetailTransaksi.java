package ui.ft.ccit.faculty.transaksi.detailtransaksi;

import jakarta.persistence.*;

@Entity
@Table(name = "detail_transaksi")
@IdClass(DetailTransaksiId.class)
public class DetailTransaksi {

    @Id
    @Column(name = "kode_transaksi", length = 4)
    private String kodeTransaksi;

    @Id
    @Column(name = "id_barang", length = 4)
    private String idBarang;

    @Column(name = "jumlah", nullable = false)
    private Short jumlah;

    protected DetailTransaksi() {
        // for JPA
    }

    public DetailTransaksi(String kodeTransaksi, String idBarang, Short jumlah) {
        this.kodeTransaksi = kodeTransaksi;
        this.idBarang = idBarang;
        this.jumlah = jumlah;
    }

    // Getters & Setters

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public Short getJumlah() {
        return jumlah;
    }

    public void setJumlah(Short jumlah) {
        this.jumlah = jumlah;
    }
}
