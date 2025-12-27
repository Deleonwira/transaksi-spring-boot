package ui.ft.ccit.faculty.transaksi.karyawan;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/karyawan")
public class KaryawanController {

    private final KaryawanService service;

    public KaryawanController(KaryawanService service) {
        this.service = service;
    }

    @GetMapping
    public List<Karyawan> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Karyawan get(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/search")
    public List<Karyawan> search(@RequestParam String q) {
        return service.searchByNama(q);
    }

    @PostMapping
    public Karyawan create(@RequestBody Karyawan karyawan) {
        return service.save(karyawan);
    }

    @PutMapping("/{id}")
    public Karyawan update(@PathVariable String id, @RequestBody Karyawan karyawan) {
        return service.update(id, karyawan);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
