package ui.ft.ccit.faculty.transaksi.pemasok;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pemasok")
public class PemasokController {

    private final PemasokService service;

    public PemasokController(PemasokService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pemasok> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Pemasok get(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public Pemasok create(@RequestBody Pemasok pemasok) {
        return service.save(pemasok);
    }

    @PutMapping("/{id}")
    public Pemasok update(@PathVariable String id, @RequestBody Pemasok pemasok) {
        return service.update(id, pemasok);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
