package gentjanahani.u2w7l2.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record PrenotazioneDTO(
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        @FutureOrPresent(message = "La data del viaggio deve essere nel futuro")
        LocalDate dataRichiesta,
        @NotBlank(message = "Le note sono un campo obbligatorio")
        @Size(min = 2, max = 100, message = "Il campo note può contenere tra i 2 e i 100 caratteri")
        String note,
        @NotNull(message = "l'id del viaggio è obbligatorio")
        UUID idViaggio,
        @NotNull(message = "l'id del dipendente è obbligatorio")
        UUID idDipendente


) {
}
