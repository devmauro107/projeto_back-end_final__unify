package unify.exception;

/**
 * Violação de regra de negócio (ex.: pagamento acima do saldo).
 * Tratada no GlobalExceptionHandler com status 400 Bad Request.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}