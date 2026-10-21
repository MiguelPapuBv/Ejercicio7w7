package utez.edu.mx.Ejercicio7w7.controller.prestamo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.Ejercicio7w7.controller.dto.RequestDevolverLibroDTO;
import utez.edu.mx.Ejercicio7w7.controller.dto.RequestRenovarPrestamoDTO;
import utez.edu.mx.Ejercicio7w7.controller.dto.RequestSolicitarPrestamoDTO;
import utez.edu.mx.Ejercicio7w7.model.prestamo.Prestamo;
import utez.edu.mx.Ejercicio7w7.service.prestamo.PrestamoService;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<Prestamo> solicitarPrestamo(@RequestBody RequestSolicitarPrestamoDTO payload) {
        return ResponseEntity.ok(prestamoService.solicitarPrestamo(payload));
    }

    @PutMapping("/devolver")
    public ResponseEntity<Prestamo> devolverLibro(@RequestBody RequestDevolverLibroDTO payload) {
        return ResponseEntity.ok(prestamoService.devolverLibro(payload));
    }

    @PutMapping("/renovar")
    public ResponseEntity<Prestamo> renovarPrestamo(@RequestBody RequestRenovarPrestamoDTO payload) {
        return ResponseEntity.ok(prestamoService.renovarPrestamo(payload));
    }

    // Las peticiones GET no llevan DTOs en el body, mantienen @PathVariable
    @GetMapping("/activos/lector/{idLector}")
    public ResponseEntity<List<Prestamo>> listarPrestamosActivos(@PathVariable Long idLector) {
        return ResponseEntity.ok(prestamoService.listarPrestamosActivosLector(idLector));
    }
}