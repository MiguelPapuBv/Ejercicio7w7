package utez.edu.mx.Ejercicio7w7.controller.lector;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.Ejercicio7w7.controller.dto.RequestRehabilitarLectorDTO;
import utez.edu.mx.Ejercicio7w7.model.lector.Lector;
import utez.edu.mx.Ejercicio7w7.service.lector.LectorService;

@RestController
@RequestMapping("/lectores")
public class LectorController {

    private final LectorService lectorService;

    public LectorController(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Lector> crearLector(@RequestBody Lector lector) {
        return ResponseEntity.ok(lectorService.crearLector(lector));
    }
    
    @PutMapping("/rehabilitar")
    public ResponseEntity<Lector> rehabilitarLector(@RequestBody RequestRehabilitarLectorDTO payload) {
        return ResponseEntity.ok(lectorService.rehabilitarLector(payload));
    }
}