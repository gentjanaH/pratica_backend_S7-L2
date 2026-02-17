package gentjanahani.u2w7l2.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public record UpdateDipendenteDTO(
        @NotBlank(message = "Lo username è un campo obbligatorio")
        @Size(min = 2, max = 30, message = "Lo username deve essere tra i 2 e i 30 caratteri")
        String username,
        @NotBlank(message = "Il nome è un campo obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome proprio deve essere tra i 2 e i 30 caratteri")
        String name,
        @NotBlank(message = "Il cognome è un campo obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve essere tra i 2 e i 30 caratteri")
        String surname

) {

}
