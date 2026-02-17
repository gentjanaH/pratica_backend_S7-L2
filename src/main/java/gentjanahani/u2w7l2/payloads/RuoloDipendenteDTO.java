package gentjanahani.u2w7l2.payloads;

import gentjanahani.u2w7l2.entities.Ruoli;
import jakarta.validation.constraints.NotNull;

public record RuoloDipendenteDTO(
        @NotNull
        Ruoli role
) {
}
