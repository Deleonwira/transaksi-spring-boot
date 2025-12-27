package ui.ft.ccit.faculty.transaksi.security;

import ui.ft.ccit.faculty.transaksi.pengguna.Pengguna;
import ui.ft.ccit.faculty.transaksi.pengguna.PenggunaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Helper class untuk mendapatkan informasi user yang sedang login.
 */
@Component
public class AuthHelper {

    private final PenggunaRepository penggunaRepository;

    public AuthHelper(PenggunaRepository penggunaRepository) {
        this.penggunaRepository = penggunaRepository;
    }

    /**
     * Mendapatkan username dari user yang sedang login.
     */
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName();
        }
        return null;
    }

    /**
     * Mendapatkan Pengguna entity dari user yang sedang login.
     */
    public Optional<Pengguna> getCurrentPengguna() {
        String username = getCurrentUsername();
        if (username != null) {
            return penggunaRepository.findByUsername(username);
        }
        return Optional.empty();
    }

    /**
     * Mendapatkan ID Pelanggan dari user yang sedang login (jika ada).
     */
    public String getCurrentPelangganId() {
        return getCurrentPengguna()
                .filter(p -> p.getPelanggan() != null)
                .map(p -> p.getPelanggan().getIdPelanggan())
                .orElse(null);
    }

    /**
     * Mendapatkan ID Karyawan dari user yang sedang login (jika ada).
     */
    public String getCurrentKaryawanId() {
        return getCurrentPengguna()
                .filter(p -> p.getKaryawan() != null)
                .map(p -> p.getKaryawan().getIdKaryawan())
                .orElse(null);
    }

    /**
     * Mendapatkan ID Pemasok dari user yang sedang login (jika ada).
     */
    public String getCurrentPemasokId() {
        return getCurrentPengguna()
                .filter(p -> p.getPemasok() != null)
                .map(p -> p.getPemasok().getIdPemasok())
                .orElse(null);
    }

    /**
     * Cek apakah user yang login memiliki role tertentu.
     */
    public boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
        }
        return false;
    }

    /**
     * Cek apakah user yang login adalah ADMIN atau KARYAWAN.
     */
    public boolean isStaff() {
        return hasRole("ADMIN") || hasRole("KARYAWAN");
    }
}
