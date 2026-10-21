package utez.edu.mx.Ejercicio7w7.exception.customException;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message){
        super(message);

    }
}