package gentjanahani.u2w7l2.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazione")
@Getter
@Setter
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idPrenotazione;
    private LocalDate dataRichiesta;
    private String note;

    //relazione ManyToOne con viaggio
    @ManyToOne
    @JoinColumn(name = "idViaggio")
    private Viaggio viaggio;

    //relazione ManyToOne con dipendente
    @ManyToOne
    @JoinColumn(name = "idUtente")
    private Dipendente dipendente;

    public Prenotazione(LocalDate dataRichiesta, String note, Viaggio viaggio, Dipendente dipendente) {
        this.dataRichiesta = dataRichiesta;
        this.note = note;
        this.viaggio = viaggio;
        this.dipendente = dipendente;
    }
}
