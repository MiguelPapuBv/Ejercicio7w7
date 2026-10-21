package utez.edu.mx.Ejercicio7w7.service.prestamo;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import utez.edu.mx.Ejercicio7w7.controller.dto.RequestDevolverLibroDTO;
import utez.edu.mx.Ejercicio7w7.controller.dto.RequestRenovarPrestamoDTO;
import utez.edu.mx.Ejercicio7w7.controller.dto.RequestSolicitarPrestamoDTO;
import utez.edu.mx.Ejercicio7w7.exception.ReglaNegocioException;
import utez.edu.mx.Ejercicio7w7.model.lector.Lector;
import utez.edu.mx.Ejercicio7w7.model.libro.Libro;
import utez.edu.mx.Ejercicio7w7.model.prestamo.EstadoPrestamo;
import utez.edu.mx.Ejercicio7w7.model.prestamo.Prestamo;
import utez.edu.mx.Ejercicio7w7.repository.lector.LectorRepository;
import utez.edu.mx.Ejercicio7w7.repository.libro.LibroRepository;
import utez.edu.mx.Ejercicio7w7.repository.prestamo.PrestamoRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, LectorRepository lectorRepository, LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional
    public Prestamo solicitarPrestamo(RequestSolicitarPrestamoDTO payload) {
        Lector lector = lectorRepository.findById(payload.getIdLector())
                .orElseThrow(() -> new ReglaNegocioException("El lector no existe."));

        if (!lector.isActivo()) {
            throw new ReglaNegocioException("El lector no está activo.");
        }

        Libro libro = libroRepository.findById(payload.getIdLibro())
                .orElseThrow(() -> new ReglaNegocioException("El libro no existe."));

        if (libro.getEjemplaresDisponibles() < 1) {
            throw new ReglaNegocioException("No hay ejemplares disponibles de este libro.");
        }

        List<EstadoPrestamo> estadosActivos = Arrays.asList(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO);
        int prestamosActivos = prestamoRepository.countByLectorIdAndEstadoIn(payload.getIdLector(), estadosActivos);
        int limite = lector.isMembresiaPremium() ? 5 : 3;

        if (prestamosActivos >= limite) {
            throw new ReglaNegocioException("El lector ha alcanzado el límite de préstamos permitidos.");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucionMaxima(LocalDate.now().plusDays(14));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolverLibro(RequestDevolverLibroDTO payload) {
        Prestamo prestamo = prestamoRepository.findById(payload.getIdPrestamo())
                .orElseThrow(() -> new ReglaNegocioException("El préstamo no existe."));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new ReglaNegocioException("El libro ya fue devuelto.");
        }

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepository.save(libro);

        if (LocalDate.now().isAfter(prestamo.getFechaDevolucionMaxima())) {
            Lector lector = prestamo.getLector();
            lector.setActivo(false);
            lectorRepository.save(lector);
        }

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo renovarPrestamo(RequestRenovarPrestamoDTO payload) {
        Prestamo prestamo = prestamoRepository.findById(payload.getIdPrestamo())
                .orElseThrow(() -> new ReglaNegocioException("El préstamo no existe."));

        if (prestamo.getEstado() == EstadoPrestamo.VENCIDO || prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new ReglaNegocioException("No se puede renovar un préstamo vencido o devuelto.");
        }

        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));
        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarPrestamosActivosLector(Long idLector) {
        return prestamoRepository.findByLectorIdAndEstadoIn(
                idLector,
                Arrays.asList(EstadoPrestamo.PRESTADO, EstadoPrestamo.VENCIDO)
        );
    }
}