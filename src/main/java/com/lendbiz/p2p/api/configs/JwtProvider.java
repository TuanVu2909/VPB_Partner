//package com.lendbiz.p2p.api.configs;
//
//import com.lendbiz.p2p.api.exception.BusinessException;
//import com.lendbiz.p2p.api.model.MyUserDetails;
//import io.jsonwebtoken.*;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//@Component
//public class JwtProvider {
//
//    String jSecret = "lendbiz";
//
//    public String crateToken( Authentication authentication) {
//        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
//        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS512, jSecret).compact();
//    }
//
//    public Boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(jSecret).parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException e) {
//            throw new BusinessException("01", "Expire Jwt token");
//        }catch (UnsupportedJwtException e) {
//            throw new BusinessException("01", "Unsupported Jwt token");
//        }catch (MalformedJwtException e) {
//            throw new BusinessException("01", "Invalid format token");
//        }catch (SignatureException e) {
//            throw new BusinessException("01", "Invalid Jwt signature");
//        }catch (IllegalArgumentException e) {
//            throw new BusinessException("01", "jwt claims String is empty");
//        }
//    }
//    public String getUserNameByToken(String token){
//        String userName = Jwts.parser().setSigningKey(jSecret).parseClaimsJws(token).getBody().getSubject();
//        return userName;
//    }
//}
