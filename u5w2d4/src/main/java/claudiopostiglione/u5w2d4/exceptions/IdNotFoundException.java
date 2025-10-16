package claudiopostiglione.u5w2d4.exceptions;

import java.util.UUID;

public class IdNotFoundException extends RuntimeException{
        public IdNotFoundException(UUID id){
            super("La risorsa con id " + id + " non è stata trovata");
        }
}
