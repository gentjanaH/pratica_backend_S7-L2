package gentjanahani.u2w7l2.security;

import gentjanahani.u2w7l2.entities.Dipendente;
import gentjanahani.u2w7l2.exceptions.UnautorizedException;
import gentjanahani.u2w7l2.services.DipendenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckerfilter extends OncePerRequestFilter {
    private final CreateAndVerify createAndVerify;
    private final DipendenteService dipendenteService;

    @Autowired
    public JWTCheckerfilter(CreateAndVerify createAndVerify, DipendenteService dipendenteService) {
        this.createAndVerify = createAndVerify;
        this.dipendenteService = dipendenteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        //verifico che la richiesta contenga il token e che sia nel formato corretto
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnautorizedException("errore nell'inserimento del token");
        //estraggo il token dall'header
        String accessToken = authHeader.substring(7);
        //verifico che il token sia valido
        createAndVerify.verifyToken(accessToken);
        //se tutto ok, andiamo avanti

        //------------------------AUTORIZZAZIONE------------------
        //Prima leggiamo l'id
        UUID dipiD = createAndVerify.extractIdFromToken(accessToken);
        Dipendente dipAutenticato = this.dipendenteService.findDipendenteById(dipiD);

        Authentication authentication = new UsernamePasswordAuthenticationToken(dipAutenticato, null, dipAutenticato.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
