package ui.ft.ccit.faculty.transaksi.detailtransaksi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailTransaksiRepository extends JpaRepository<DetailTransaksi, DetailTransaksiId> {

    List<DetailTransaksi> findByKodeTransaksi(String kodeTransaksi);

    List<DetailTransaksi> findByIdBarang(String idBarang);
}
