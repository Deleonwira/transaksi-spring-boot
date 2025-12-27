package ui.ft.ccit.faculty.transaksi.jenisbarang;

import ui.ft.ccit.faculty.transaksi.DataAlreadyExistsException;
import ui.ft.ccit.faculty.transaksi.DataNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JenisBarangService {

    private final JenisBarangRepository repository;

    public JenisBarangService(JenisBarangRepository repository) {
        this.repository = repository;
    }

    public List<JenisBarang> getAll() {
        return repository.findAll();
    }

    public JenisBarang getById(Byte id) {
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("JenisBarang", String.valueOf(id)));
    }

    public JenisBarang save(JenisBarang jenisBarang) {
        if (jenisBarang.getIdJenisBarang() != null && repository.existsById(jenisBarang.getIdJenisBarang())) {
            throw new DataAlreadyExistsException("JenisBarang", String.valueOf(jenisBarang.getIdJenisBarang()));
        }
        return repository.save(jenisBarang);
    }

    public JenisBarang update(Byte id, JenisBarang updated) {
        JenisBarang existing = getById(id);
        existing.setNamaJenis(updated.getNamaJenis());
        return repository.save(existing);
    }

    public void delete(Byte id) {
        if (!repository.existsById(id)) {
            throw new DataNotFoundException("JenisBarang", String.valueOf(id));
        }
        repository.deleteById(id);
    }
}
