package ui.ft.ccit.faculty.transaksi.pemasok;

import ui.ft.ccit.faculty.transaksi.DataAlreadyExistsException;
import ui.ft.ccit.faculty.transaksi.DataNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PemasokService {

    private final PemasokRepository repository;

    public PemasokService(PemasokRepository repository) {
        this.repository = repository;
    }

    public List<Pemasok> getAll() {
        return repository.findAll();
    }

    public Pemasok getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Pemasok", id));
    }

    public Pemasok save(Pemasok pemasok) {
        if (pemasok.getIdPemasok() == null || pemasok.getIdPemasok().isBlank()) {
            throw new IllegalArgumentException("idPemasok wajib diisi");
        }
        if (repository.existsById(pemasok.getIdPemasok())) {
            throw new DataAlreadyExistsException("Pemasok", pemasok.getIdPemasok());
        }
        return repository.save(pemasok);
    }

    public Pemasok update(String id, Pemasok updated) {
        Pemasok existing = getById(id);
        existing.setNamaPemasok(updated.getNamaPemasok());
        existing.setAlamat(updated.getAlamat());
        existing.setTelepon(updated.getTelepon());
        existing.setEmail(updated.getEmail());
        return repository.save(existing);
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new DataNotFoundException("Pemasok", id);
        }
        repository.deleteById(id);
    }
}
