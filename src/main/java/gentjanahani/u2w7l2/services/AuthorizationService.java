package gentjanahani.u2w7l2.services;

import gentjanahani.u2w7l2.entities.Dipendente;
import gentjanahani.u2w7l2.exceptions.UnautorizedException;
import gentjanahani.u2w7l2.payloads.LoginDTO;
import gentjanahani.u2w7l2.security.CreateAndVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final DipendenteService dipendenteService;
    private final CreateAndVerify createAndVerify;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationService(DipendenteService dipendenteService, CreateAndVerify createAndVerify, PasswordEncoder passwordEncoder) {
        this.dipendenteService = dipendenteService;
        this.createAndVerify = createAndVerify;
        this.passwordEncoder = passwordEncoder;
    }

    public String checkAndGenerate(LoginDTO bodyLogin) {
        Dipendente dip = this.dipendenteService.findByEmail(bodyLogin.mail());

        if (passwordEncoder.matches(bodyLogin.password(), dip.getPassword())) {
            String accesToken = createAndVerify.generateToken(dip);

            return accesToken;
        } else {
            throw new UnautorizedException("Credenziali non valide");
        }
    }
}
