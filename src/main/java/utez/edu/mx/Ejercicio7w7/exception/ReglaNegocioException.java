package utez.edu.mx.Ejercicio7w7.exception;

// Excepción personalizada: se lanza cuando no se cumple una regla de negocio
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}