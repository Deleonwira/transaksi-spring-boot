package ui.ft.ccit.faculty.transaksi.pelanggan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PelangganRepository extends JpaRepository<Pelanggan, String> {

    List<Pelanggan> findByNamaContainingIgnoreCase(String keyword);

    List<Pelanggan> findByJenisPelanggan(String jenisPelanggan);
}
