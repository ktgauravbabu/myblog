package com.myblog5.myblog5.security;

import com.myblog5.myblog5.exception.BlogAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;
@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-Milliseconds}")
    private String jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.ES512, jwtSecret)
                .compact();
        return token;
    }

        public String getUsernameFromToken (String token){
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }
        public boolean validateToken (String token) throws BlogAPIException {
            try {
                Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
                return true;
            } catch (SignatureException ex) {
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
            }catch(MalformedJwtException ex){
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
            }catch(ExpiredJwtException ex) {
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
            }catch(UnsupportedJwtException ex) {
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
            }catch (IllegalArgumentException ex){
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Illegal JWT token");
            }

        }
    }





