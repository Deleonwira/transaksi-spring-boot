package ui.ft.ccit.faculty.transaksi.pengguna;

import jakarta.persistence.*;
import ui.ft.ccit.faculty.transaksi.karyawan.Karyawan;
import ui.ft.ccit.faculty.transaksi.pelanggan.Pelanggan;
import ui.ft.ccit.faculty.transaksi.pemasok.Pemasok;

import java.time.LocalDateTime;

@Entity
@Table(name = "pengguna")
public class Pengguna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pengguna")
    private Integer idPengguna;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "role", length = 20, nullable = false)
    private String role = "KARYAWAN";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_karyawan")
    private Karyawan karyawan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelanggan")
    private Pelanggan pelanggan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pemasok")
    private Pemasok pemasok;

    @Column(name = "aktif", nullable = false)
    private Boolean aktif = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    protected Pengguna() {
        // for JPA
    }

    public Pengguna(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.aktif = true;
    }

    // Getters & Setters

    public Integer getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(Integer idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Karyawan getKaryawan() {
        return karyawan;
    }

    public void setKaryawan(Karyawan karyawan) {
        this.karyawan = karyawan;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public Pemasok getPemasok() {
        return pemasok;
    }

    public void setPemasok(Pemasok pemasok) {
        this.pemasok = pemasok;
    }

    public Boolean getAktif() {
        return aktif;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Helper method to get the linked entity name
    public String getNamaEntitas() {
        if (karyawan != null) return karyawan.getNama();
        if (pelanggan != null) return pelanggan.getNama();
        if (pemasok != null) return pemasok.getNamaPemasok();
        return username;
    }
}
