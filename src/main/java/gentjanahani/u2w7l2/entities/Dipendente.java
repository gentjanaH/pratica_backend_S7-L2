package gentjanahani.u2w7l2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dipendente")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "accountNonExpired", "accountNonLocked", "authorities", "credentialsNonExpired", "enabled"})
public class Dipendente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idDipendente;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Ruoli role;

    public Dipendente(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = Ruoli.DIPENDENTE;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
}
