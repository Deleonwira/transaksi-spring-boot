package ui.ft.ccit.faculty.transaksi.detailtransaksi;

import ui.ft.ccit.faculty.transaksi.DataAlreadyExistsException;
import ui.ft.ccit.faculty.transaksi.DataNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DetailTransaksiService {

    private final DetailTransaksiRepository repository;
    private final ui.ft.ccit.faculty.transaksi.barang.BarangRepository barangRepository;

    public DetailTransaksiService(DetailTransaksiRepository repository,
                                  ui.ft.ccit.faculty.transaksi.barang.BarangRepository barangRepository) {
        this.repository = repository;
        this.barangRepository = barangRepository;
    }

    public List<DetailTransaksi> getAll() {
        return repository.findAll();
    }

    public DetailTransaksi getById(String kodeTransaksi, String idBarang) {
        DetailTransaksiId id = new DetailTransaksiId(kodeTransaksi, idBarang);
        return repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("DetailTransaksi", 
                        kodeTransaksi + "/" + idBarang));
    }

    public List<DetailTransaksi> findByTransaksi(String kodeTransaksi) {
        return repository.findByKodeTransaksi(kodeTransaksi);
    }

    public List<DetailTransaksi> findByBarang(String idBarang) {
        return repository.findByIdBarang(idBarang);
    }

    public DetailTransaksi save(DetailTransaksi detail) {
        if (detail.getKodeTransaksi() == null || detail.getKodeTransaksi().isBlank()) {
            throw new IllegalArgumentException("kodeTransaksi wajib diisi");
        }
        if (detail.getIdBarang() == null || detail.getIdBarang().isBlank()) {
            throw new IllegalArgumentException("idBarang wajib diisi");
        }

        // Cek Barang & Stok
        var barang = barangRepository.findById(detail.getIdBarang())
                .orElseThrow(() -> new DataNotFoundException("Barang", detail.getIdBarang()));

        if (barang.getStok() < detail.getJumlah()) {
            throw new IllegalArgumentException("Stok barang tidak mencukupi. Stok saat ini: " + barang.getStok());
        }

        DetailTransaksiId id = new DetailTransaksiId(detail.getKodeTransaksi(), detail.getIdBarang());
        if (repository.existsById(id)) {
            throw new DataAlreadyExistsException("DetailTransaksi", 
                    detail.getKodeTransaksi() + "/" + detail.getIdBarang());
        }

        // Kurangi Stok
        barang.setStok(barang.getStok() - detail.getJumlah());
        barangRepository.save(barang);

        return repository.save(detail);
    }

    public DetailTransaksi update(String kodeTransaksi, String idBarang, DetailTransaksi updated) {
        DetailTransaksi existing = getById(kodeTransaksi, idBarang);
        
        // Cek Barang untuk Stok
        var barang = barangRepository.findById(idBarang)
                .orElseThrow(() -> new DataNotFoundException("Barang", idBarang));

        // Selisih jumlah (Baru - Lama)
        // Jika Baru > Lama, stok berkurang (selisih positif)
        // Jika Baru < Lama, stok bertambah (selisih negatif)
        int diff = updated.getJumlah() - existing.getJumlah();

        if (diff > 0 && barang.getStok() < diff) {
             throw new IllegalArgumentException("Stok barang tidak mencukupi untuk penambahan jumlah.");
        }

        barang.setStok(barang.getStok() - diff);
        barangRepository.save(barang);

        existing.setJumlah(updated.getJumlah());
        return repository.save(existing);
    }

    public void delete(String kodeTransaksi, String idBarang) {
        DetailTransaksiId id = new DetailTransaksiId(kodeTransaksi, idBarang);
        DetailTransaksi existing = repository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("DetailTransaksi", kodeTransaksi + "/" + idBarang));

        // Kembalikan Stok
        var barang = barangRepository.findById(idBarang).orElse(null);
        if (barang != null) {
            barang.setStok(barang.getStok() + existing.getJumlah());
            barangRepository.save(barang);
        }

        repository.deleteById(id);
    }
}
