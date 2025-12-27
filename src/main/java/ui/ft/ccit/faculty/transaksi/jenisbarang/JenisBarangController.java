package ui.ft.ccit.faculty.transaksi.jenisbarang;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jenis-barang")
public class JenisBarangController {

    private final JenisBarangService service;

    public JenisBarangController(JenisBarangService service) {
        this.service = service;
    }

    @GetMapping
    public List<JenisBarang> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public JenisBarang get(@PathVariable Byte id) {
        return service.getById(id);
    }

    @PostMapping
    public JenisBarang create(@RequestBody JenisBarang jenisBarang) {
        return service.save(jenisBarang);
    }

    @PutMapping("/{id}")
    public JenisBarang update(@PathVariable Byte id, @RequestBody JenisBarang jenisBarang) {
        return service.update(id, jenisBarang);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Byte id) {
        service.delete(id);
    }
}
