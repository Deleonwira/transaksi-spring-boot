package ui.ft.ccit.faculty.transaksi.pengguna;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PenggunaRepository extends JpaRepository<Pengguna, Integer> {

    Optional<Pengguna> findByUsername(String username);

    boolean existsByUsername(String username);
}
