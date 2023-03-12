package com.chicasensistemas.auth.config.filter;


import com.chicasensistemas.model.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtils {

  private static final String SECRET_KEY = "chicasensistemas";
  private static final String BEARER_TOKEN = "Bearer %s";
  private static final String AUTHORITIES = "authorities";
  private static final String BEARER_PART = "Bearer ";
  private static final String EMPTY = "";

  public String generateToken(UserDetails userDetails) {
    UserEntity user = (UserEntity) userDetails;
    return createToken(user.getUsername(), user.getRoles().get(0).getName());
  }

  private String createToken(String subject, String role) {
    String token = Jwts.builder()
        .claim(AUTHORITIES, convertTo(AuthorityUtils.commaSeparatedStringToAuthorityList(role)))
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();
    return String.format(BEARER_TOKEN, token);
  }

  private List<String> convertTo(List<GrantedAuthority> grantedAuthorities) {
    return grantedAuthorities.stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

  }

  public boolean isTokenSet(String authorizationHeader){
    return authorizationHeader != null;
  }


  public Claims extractAllClaims(String authorizationHeader) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
        .parseClaimsJws(getToken(authorizationHeader))
        .getBody();
  }

  private String getToken(String authorizationHeader) {
    return authorizationHeader.replace(BEARER_PART, EMPTY);
  }
}
