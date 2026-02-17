package gentjanahani.u2w7l2.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AmministratoreDTO(
        @NotBlank(message = "Il nome è un campo obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome proprio deve essere tra i 2 e i 30 caratteri")
        String name,
        @NotBlank(message = "Il cognome è un campo obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve essere tra i 2 e i 30 caratteri")
        String surname,
        @NotBlank(message = "La mail è un campo obbligatorio")
        @Email(message = "L'indirizzo mail fornito non è nel formato corretto")
        String mail,
        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,}$", message = "La password deve contenere una maiuscola, una minuscola e almeno un carattere speciale")
        String password
) {
}
