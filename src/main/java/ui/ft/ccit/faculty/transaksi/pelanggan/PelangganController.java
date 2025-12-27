package ui.ft.ccit.faculty.transaksi.pelanggan;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pelanggan")
public class PelangganController {

    private final PelangganService service;

    public PelangganController(PelangganService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pelanggan> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Pelanggan get(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/search")
    public List<Pelanggan> search(@RequestParam String q) {
        return service.searchByNama(q);
    }

    @PostMapping
    public Pelanggan create(@RequestBody Pelanggan pelanggan) {
        return service.save(pelanggan);
    }

    @PutMapping("/{id}")
    public Pelanggan update(@PathVariable String id, @RequestBody Pelanggan pelanggan) {
        return service.update(id, pelanggan);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
