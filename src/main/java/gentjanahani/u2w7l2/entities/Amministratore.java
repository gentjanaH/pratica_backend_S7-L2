package gentjanahani.u2w7l2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "amministratore")
public class Amministratore {
    @Id
    @GeneratedValue
    UUID idAmministratore;
    private String Name;
    private String surname;
    private String mail;
    private String password;

    public Amministratore(String name, String surname, String mail, String password) {
        Name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
    }
}
