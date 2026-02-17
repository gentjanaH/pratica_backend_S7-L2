package gentjanahani.u2w7l2.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Nessun elemento trovato con id: " + id);
    }
    public NotFoundException(String message){
        super(message);
    }
}
