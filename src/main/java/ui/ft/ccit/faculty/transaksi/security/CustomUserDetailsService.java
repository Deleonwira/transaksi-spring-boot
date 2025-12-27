package ui.ft.ccit.faculty.transaksi.security;

import ui.ft.ccit.faculty.transaksi.pengguna.Pengguna;
import ui.ft.ccit.faculty.transaksi.pengguna.PenggunaRepository;
import ui.ft.ccit.faculty.transaksi.pelanggan.PelangganRepository;
import ui.ft.ccit.faculty.transaksi.pelanggan.Pelanggan;
import ui.ft.ccit.faculty.transaksi.DataNotFoundException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PenggunaRepository penggunaRepository;
    private final PelangganRepository pelangganRepository;

    public CustomUserDetailsService(PenggunaRepository penggunaRepository, 
                                    PelangganRepository pelangganRepository) {
        this.penggunaRepository = penggunaRepository;
        this.pelangganRepository = pelangganRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pengguna pengguna = penggunaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (!pengguna.getAktif()) {
            throw new UsernameNotFoundException("User is not active: " + username);
        }

        return User.builder()
                .username(pengguna.getUsername())
                .password(pengguna.getPassword())
                .roles(pengguna.getRole())
                .build();
    }

    public boolean existsByUsername(String username) {
        return penggunaRepository.existsByUsername(username);
    }

    @Transactional
    public void saveUser(Pengguna pengguna, String idPelanggan) {
        if (idPelanggan != null && !idPelanggan.isBlank()) {
            Pelanggan pelanggan = pelangganRepository.findById(idPelanggan)
                    .orElseThrow(() -> new DataNotFoundException("Pelanggan", idPelanggan));
            pengguna.setPelanggan(pelanggan);
        }
        penggunaRepository.save(pengguna);
    }
}
