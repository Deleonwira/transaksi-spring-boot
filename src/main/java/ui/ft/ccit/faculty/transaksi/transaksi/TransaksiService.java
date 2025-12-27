package ui.ft.ccit.faculty.transaksi.transaksi;

import ui.ft.ccit.faculty.transaksi.DataAlreadyExistsException;
import ui.ft.ccit.faculty.transaksi.DataNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransaksiService {

    private final TransaksiRepository repository;

    public TransaksiService(TransaksiRepository repository) {
        this.repository = repository;
    }

    public List<Transaksi> getAll() {
        return repository.findAll();
    }

    public Transaksi getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Transaksi", id));
    }

    public List<Transaksi> findByPelanggan(String idPelanggan) {
        return repository.findByIdPelanggan(idPelanggan);
    }

    public List<Transaksi> findByKaryawan(String idKaryawan) {
        return repository.findByIdKaryawan(idKaryawan);
    }

    public Transaksi save(Transaksi transaksi) {
        if (transaksi.getKodeTransaksi() == null || transaksi.getKodeTransaksi().isBlank()) {
            throw new IllegalArgumentException("kodeTransaksi wajib diisi");
        }
        if (repository.existsById(transaksi.getKodeTransaksi())) {
            throw new DataAlreadyExistsException("Transaksi", transaksi.getKodeTransaksi());
        }
        return repository.save(transaksi);
    }

    public Transaksi update(String id, Transaksi updated) {
        Transaksi existing = getById(id);
        existing.setTglTransaksi(updated.getTglTransaksi());
        existing.setIdPelanggan(updated.getIdPelanggan());
        existing.setIdKaryawan(updated.getIdKaryawan());
        return repository.save(existing);
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new DataNotFoundException("Transaksi", id);
        }
        repository.deleteById(id);
    }
}
