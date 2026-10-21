package utez.edu.mx.Ejercicio7w7.service.lector;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import utez.edu.mx.Ejercicio7w7.controller.dto.RequestRehabilitarLectorDTO;
import utez.edu.mx.Ejercicio7w7.exception.ReglaNegocioException;
import utez.edu.mx.Ejercicio7w7.model.lector.Lector;
import utez.edu.mx.Ejercicio7w7.repository.lector.LectorRepository;

@Service
public class LectorService {

    public Lector crearLector(Lector lector) {
        return lectorRepository.save(lector);
    }

    private final LectorRepository lectorRepository;

    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    @Transactional
    public Lector rehabilitarLector(RequestRehabilitarLectorDTO payload) {
        Lector lector = lectorRepository.findById(payload.getIdLector())
                .orElseThrow(() -> new ReglaNegocioException("El lector no existe."));

        lector.setActivo(true);
        return lectorRepository.save(lector);
    }
}