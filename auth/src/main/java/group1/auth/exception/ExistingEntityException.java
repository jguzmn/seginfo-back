package group1.auth.exception;

public class ExistingEntityException extends RuntimeException {

    public ExistingEntityException (String id) {
        super("No es posible el registro, '"+ id + "' ya existe en la base de datos");
    }
}
