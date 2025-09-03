package serene.exception;

public class SereneException extends Exception {
    public SereneException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.getMessage();
    }
}

