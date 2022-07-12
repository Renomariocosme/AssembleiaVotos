package br.com.desafiosolutis.security;

import br.com.desafiosolutis.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private String expiration;
    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication){
        Usuario logado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));


        return Jwts.builder()
                .setIssuer("API Assembleia de votos")
                .setSubject(logado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public boolean isValidaToken(String token){
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Integer getIdUsuario(String token){
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }

}
