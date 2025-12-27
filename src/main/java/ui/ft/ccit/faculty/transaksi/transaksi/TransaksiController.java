package ui.ft.ccit.faculty.transaksi.transaksi;

import ui.ft.ccit.faculty.transaksi.security.AuthHelper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {

    private final TransaksiService service;
    private final AuthHelper authHelper;

    public TransaksiController(TransaksiService service, AuthHelper authHelper) {
        this.service = service;
        this.authHelper = authHelper;
    }

    @GetMapping
    public List<Transaksi> list() {
        // Jika PELANGGAN, hanya tampilkan transaksi miliknya
        if (authHelper.hasRole("PELANGGAN")) {
            String idPelanggan = authHelper.getCurrentPelangganId();
            if (idPelanggan == null) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pelanggan ID not linked to account");
            }
            return service.findByPelanggan(idPelanggan);
        }
        // ADMIN/KARYAWAN: tampilkan semua
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Transaksi get(@PathVariable String id) {
        Transaksi transaksi = service.getById(id);
        
        // Jika PELANGGAN, cek apakah transaksi miliknya
        if (authHelper.hasRole("PELANGGAN")) {
            String idPelanggan = authHelper.getCurrentPelangganId();
            if (idPelanggan == null || !idPelanggan.equals(transaksi.getIdPelanggan())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied to this transaction");
            }
        }
        
        return transaksi;
    }

    @GetMapping("/pelanggan/{idPelanggan}")
    public List<Transaksi> findByPelanggan(@PathVariable String idPelanggan) {
        // Jika PELANGGAN, hanya bisa lihat miliknya sendiri
        if (authHelper.hasRole("PELANGGAN")) {
            String currentPelangganId = authHelper.getCurrentPelangganId();
            if (!idPelanggan.equals(currentPelangganId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
            }
        }
        return service.findByPelanggan(idPelanggan);
    }

    @GetMapping("/karyawan/{idKaryawan}")
    public List<Transaksi> findByKaryawan(@PathVariable String idKaryawan) {
        // Hanya ADMIN/KARYAWAN yang bisa akses endpoint ini (sudah di SecurityConfig)
        return service.findByKaryawan(idKaryawan);
    }

    @PostMapping
    public Transaksi create(@RequestBody Transaksi transaksi) {
        // Hanya ADMIN/KARYAWAN yang bisa create (sudah di method-level security kalau diperlukan)
        if (authHelper.hasRole("PELANGGAN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pelanggan cannot create transactions");
        }
        return service.save(transaksi);
    }

    @PutMapping("/{id}")
    public Transaksi update(@PathVariable String id, @RequestBody Transaksi transaksi) {
        if (authHelper.hasRole("PELANGGAN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pelanggan cannot update transactions");
        }
        return service.update(id, transaksi);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        if (authHelper.hasRole("PELANGGAN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pelanggan cannot delete transactions");
        }
        service.delete(id);
    }
}
