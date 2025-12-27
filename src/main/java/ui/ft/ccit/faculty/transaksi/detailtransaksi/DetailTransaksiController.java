package ui.ft.ccit.faculty.transaksi.detailtransaksi;

import ui.ft.ccit.faculty.transaksi.security.AuthHelper;
import ui.ft.ccit.faculty.transaksi.transaksi.TransaksiService;
import ui.ft.ccit.faculty.transaksi.transaksi.Transaksi;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detail-transaksi")
public class DetailTransaksiController {

    private final DetailTransaksiService service;
    private final TransaksiService transaksiService;
    private final AuthHelper authHelper;

    public DetailTransaksiController(DetailTransaksiService service,
                                     TransaksiService transaksiService,
                                     AuthHelper authHelper) {
        this.service = service;
        this.transaksiService = transaksiService;
        this.authHelper = authHelper;
    }

    @GetMapping
    public List<DetailTransaksi> list() {
        // Jika PELANGGAN, hanya tampilkan detail dari transaksi miliknya
        if (authHelper.hasRole("PELANGGAN")) {
            String idPelanggan = authHelper.getCurrentPelangganId();
            if (idPelanggan == null) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pelanggan ID not linked");
            }
            // Ambil semua transaksi milik pelanggan
            List<Transaksi> myTransactions = transaksiService.findByPelanggan(idPelanggan);
            List<String> myKodeTransaksi = myTransactions.stream()
                    .map(Transaksi::getKodeTransaksi)
                    .collect(Collectors.toList());
            
            // Filter detail berdasarkan transaksi milik pelanggan
            return service.getAll().stream()
                    .filter(d -> myKodeTransaksi.contains(d.getKodeTransaksi()))
                    .collect(Collectors.toList());
        }
        return service.getAll();
    }

    @GetMapping("/{kodeTransaksi}/{idBarang}")
    public DetailTransaksi get(@PathVariable String kodeTransaksi, @PathVariable String idBarang) {
        // Cek ownership jika PELANGGAN
        if (authHelper.hasRole("PELANGGAN")) {
            validateTransactionOwnership(kodeTransaksi);
        }
        return service.getById(kodeTransaksi, idBarang);
    }

    @GetMapping("/transaksi/{kodeTransaksi}")
    public List<DetailTransaksi> findByTransaksi(@PathVariable String kodeTransaksi) {
        // Cek ownership jika PELANGGAN
        if (authHelper.hasRole("PELANGGAN")) {
            validateTransactionOwnership(kodeTransaksi);
        }
        return service.findByTransaksi(kodeTransaksi);
    }

    @GetMapping("/barang/{idBarang}")
    public List<DetailTransaksi> findByBarang(@PathVariable String idBarang) {
        // Jika PELANGGAN, filter berdasarkan transaksi miliknya
        if (authHelper.hasRole("PELANGGAN")) {
            String idPelanggan = authHelper.getCurrentPelangganId();
            List<Transaksi> myTransactions = transaksiService.findByPelanggan(idPelanggan);
            List<String> myKodeTransaksi = myTransactions.stream()
                    .map(Transaksi::getKodeTransaksi)
                    .collect(Collectors.toList());
            
            return service.findByBarang(idBarang).stream()
                    .filter(d -> myKodeTransaksi.contains(d.getKodeTransaksi()))
                    .collect(Collectors.toList());
        }
        return service.findByBarang(idBarang);
    }

    @PostMapping
    public DetailTransaksi create(@RequestBody DetailTransaksi detail) {
        if (authHelper.hasRole("PELANGGAN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pelanggan cannot create detail transaksi");
        }
        return service.save(detail);
    }

    @PutMapping("/{kodeTransaksi}/{idBarang}")
    public DetailTransaksi update(@PathVariable String kodeTransaksi, 
                                  @PathVariable String idBarang,
                                  @RequestBody DetailTransaksi detail) {
        if (authHelper.hasRole("PELANGGAN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pelanggan cannot update detail transaksi");
        }
        return service.update(kodeTransaksi, idBarang, detail);
    }

    @DeleteMapping("/{kodeTransaksi}/{idBarang}")
    public void delete(@PathVariable String kodeTransaksi, @PathVariable String idBarang) {
        if (authHelper.hasRole("PELANGGAN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pelanggan cannot delete detail transaksi");
        }
        service.delete(kodeTransaksi, idBarang);
    }

    private void validateTransactionOwnership(String kodeTransaksi) {
        String idPelanggan = authHelper.getCurrentPelangganId();
        if (idPelanggan == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Pelanggan ID not linked");
        }
        Transaksi transaksi = transaksiService.getById(kodeTransaksi);
        if (!idPelanggan.equals(transaksi.getIdPelanggan())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied to this transaction");
        }
    }
}
