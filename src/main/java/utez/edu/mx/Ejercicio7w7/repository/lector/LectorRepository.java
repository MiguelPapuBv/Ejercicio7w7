package utez.edu.mx.Ejercicio7w7.repository.lector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.Ejercicio7w7.model.lector.Lector;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
}