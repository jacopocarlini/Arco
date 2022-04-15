package com.jacopocarlini.arco.service;

import com.jacopocarlini.arco.entity.User;
import com.jacopocarlini.arco.exception.AppError;
import com.jacopocarlini.arco.exception.AppException;
import com.jacopocarlini.arco.model.UserAuth;
import com.jacopocarlini.arco.model.UserDetails;
import com.jacopocarlini.arco.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Value("${jwt.validity.days}")
    private Integer tokenValidityDays;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    public UserDetails signup(UserDetails userDetails) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDetails.getPassword());
        userDetails.setPassword(encryptedPassword); // encrypted password before save
        userRepository.save(modelMapper.map(userDetails, User.class));
        userDetails.setPassword(null); // remove password from response
        return userDetails;
    }

    public String login(UserAuth userAuth) {
        var userEntity = userRepository.findByEmail(userAuth.getEmail())
                .orElseThrow(() -> new AppException(AppError.UNATHORIZED)); // not use USER_NOT_FOUND because it's a security error

        String encryptedPassword = new BCryptPasswordEncoder().encode(userAuth.getPassword());
        if (encryptedPassword.equals(userEntity.getPassword())) {
            Map<String, Object> claims = new HashMap<>();

            return generateJwt(userEntity.getEmail(), claims);
        } else {
            throw new AppException(AppError.UNATHORIZED);
        }
    }

    private String generateJwt(String email, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityDays * 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateJwt(String token) {
        var claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration().after(new Date());
    }

}
