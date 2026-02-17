package gentjanahani.u2w7l2.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ViaggioDTO(
        @NotBlank(message="La destinazione è un campo obbligatorio")
        @Size(min=2, max=30, message="La destinazione deve essere tra i 2 e i 30 caratteri")
        String destination,
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        @FutureOrPresent(message = "La data del viaggio deve essere nel futuro")
        LocalDate travelDate) {
}
