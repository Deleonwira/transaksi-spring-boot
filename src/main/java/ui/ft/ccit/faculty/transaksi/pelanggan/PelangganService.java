package ui.ft.ccit.faculty.transaksi.pelanggan;

import ui.ft.ccit.faculty.transaksi.DataAlreadyExistsException;
import ui.ft.ccit.faculty.transaksi.DataNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PelangganService {

    private final PelangganRepository repository;

    public PelangganService(PelangganRepository repository) {
        this.repository = repository;
    }

    public List<Pelanggan> getAll() {
        return repository.findAll();
    }

    public Pelanggan getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Pelanggan", id));
    }

    public List<Pelanggan> searchByNama(String keyword) {
        return repository.findByNamaContainingIgnoreCase(keyword);
    }

    public Pelanggan save(Pelanggan pelanggan) {
        if (pelanggan.getIdPelanggan() == null || pelanggan.getIdPelanggan().isBlank()) {
            throw new IllegalArgumentException("idPelanggan wajib diisi");
        }
        if (repository.existsById(pelanggan.getIdPelanggan())) {
            throw new DataAlreadyExistsException("Pelanggan", pelanggan.getIdPelanggan());
        }
        return repository.save(pelanggan);
    }

    public Pelanggan update(String id, Pelanggan updated) {
        Pelanggan existing = getById(id);
        existing.setNama(updated.getNama());
        existing.setJenisKelamin(updated.getJenisKelamin());
        existing.setAlamat(updated.getAlamat());
        existing.setTelepon(updated.getTelepon());
        existing.setTglLahir(updated.getTglLahir());
        existing.setJenisPelanggan(updated.getJenisPelanggan());
        return repository.save(existing);
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new DataNotFoundException("Pelanggan", id);
        }
        repository.deleteById(id);
    }
}
