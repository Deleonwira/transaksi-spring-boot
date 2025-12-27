package ui.ft.ccit.faculty.transaksi.karyawan;

import ui.ft.ccit.faculty.transaksi.DataAlreadyExistsException;
import ui.ft.ccit.faculty.transaksi.DataNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KaryawanService {

    private final KaryawanRepository repository;

    public KaryawanService(KaryawanRepository repository) {
        this.repository = repository;
    }

    public List<Karyawan> getAll() {
        return repository.findAll();
    }

    public Karyawan getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Karyawan", id));
    }

    public List<Karyawan> searchByNama(String keyword) {
        return repository.findByNamaContainingIgnoreCase(keyword);
    }

    public Karyawan save(Karyawan karyawan) {
        if (karyawan.getIdKaryawan() == null || karyawan.getIdKaryawan().isBlank()) {
            throw new IllegalArgumentException("idKaryawan wajib diisi");
        }
        if (repository.existsById(karyawan.getIdKaryawan())) {
            throw new DataAlreadyExistsException("Karyawan", karyawan.getIdKaryawan());
        }
        return repository.save(karyawan);
    }

    public Karyawan update(String id, Karyawan updated) {
        Karyawan existing = getById(id);
        existing.setNama(updated.getNama());
        existing.setJenisKelamin(updated.getJenisKelamin());
        existing.setAlamat(updated.getAlamat());
        existing.setTelepon(updated.getTelepon());
        existing.setTglLahir(updated.getTglLahir());
        existing.setGaji(updated.getGaji());
        return repository.save(existing);
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new DataNotFoundException("Karyawan", id);
        }
        repository.deleteById(id);
    }
}
