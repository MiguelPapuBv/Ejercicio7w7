package utez.edu.mx.Ejercicio7w7.service.libro;

import org.springframework.stereotype.Service;
import utez.edu.mx.Ejercicio7w7.model.libro.Libro;
import utez.edu.mx.Ejercicio7w7.repository.libro.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public Libro crearLibro(Libro libro) {
        return libroRepository.save(libro);
    }
}