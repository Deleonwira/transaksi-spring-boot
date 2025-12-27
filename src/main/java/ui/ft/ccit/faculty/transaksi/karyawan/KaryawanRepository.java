package ui.ft.ccit.faculty.transaksi.karyawan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KaryawanRepository extends JpaRepository<Karyawan, String> {

    List<Karyawan> findByNamaContainingIgnoreCase(String keyword);
}
