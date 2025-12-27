package ui.ft.ccit.faculty.transaksi.transaksi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaksiRepository extends JpaRepository<Transaksi, String> {

    List<Transaksi> findByIdPelanggan(String idPelanggan);

    List<Transaksi> findByIdKaryawan(String idKaryawan);
}
