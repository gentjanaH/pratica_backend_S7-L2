package gentjanahani.u2w7l2.security;

import gentjanahani.u2w7l2.exceptions.UnautorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckerfilter extends OncePerRequestFilter {
     private final CreateAndVerify createAndVerify;

     @Autowired
    public JWTCheckerfilter(CreateAndVerify createAndVerify) {
        this.createAndVerify = createAndVerify;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

         String authHeader= request.getHeader("Authorization");

         //verifico che la richiesta contenga il token e che sia nel formato corretto
         if(authHeader== null || !authHeader.startsWith("Bearer ")) throw new UnautorizedException("errore nell'inserimento del token");
         //estraggo il token dall'header
        String accessToke=authHeader.substring(7);
        //verifico che il token sia valido
        createAndVerify.verifyToken(accessToke);
        //se tutto ok, andiamo avanti
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
