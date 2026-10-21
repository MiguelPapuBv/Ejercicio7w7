package utez.edu.mx.Ejercicio7w7.repository.libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.Ejercicio7w7.model.libro.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}