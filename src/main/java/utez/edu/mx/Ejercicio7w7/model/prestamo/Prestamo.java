package utez.edu.mx.Ejercicio7w7.model.prestamo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utez.edu.mx.Ejercicio7w7.model.lector.Lector;
import utez.edu.mx.Ejercicio7w7.model.libro.Libro;

import java.time.LocalDate;

@Entity
@Table(name="prestamos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionMaxima;

    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado;

    @ManyToOne
    @JoinColumn(name="lector_id")
    private Lector lector;

    @ManyToOne
    @JoinColumn(name="libro_id")
    private Libro libro;

}