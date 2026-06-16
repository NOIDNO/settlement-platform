package com.ohy.settlement_platform.common.jwt;

import com.ohy.settlement_platform.common.exception.GlobalExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * JWT 생성 및 검증을 담당하는 유틸 클래스
 *
 * - 로그인 성공 시 JWT 토큰 발급
 * - 토큰에서 사용자 정보(email, role) 추출
 * - 토큰 유효성 검증
 */
@Component
public class JwtUtil {
    private final GlobalExceptionHandler globalExceptionHandler;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt. access-expiration}")
    private long accessExpiration;

    @Value("${jwt. refresh-expiration}")
    private long refreshExpiration;


    JwtUtil(GlobalExceptionHandler globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }
    
  
    /**
     * Access Token 생성
     * @param email
     * @param role
     * @return 생성된 JWT Access Token 문자열
     */
    public String generateAccessToken(String email, String role){
        return generateToken(email, role, accessExpiration, "access" );
    }

      /**
     * Access Token 생성
     * @param email
     * @param role
     * @return 생성된 JWT Refresh Token 문자열
     */
   public String generateRefreshToken(String email){
        //재발급할 때만 쓰이고 권한 체크 X
        return generateToken(email, null, refreshExpiration, "refresh" );
    }



    /**
     * JWT 생성 (로그인 성공 시 사용)
     * email, role 정보를 토큰에 포함
     */
    public String generateToken(String email, String role, long expiration, String type) {
         JwtBuilder builder = Jwts.builder()
                .setSubject(email)
                .claim("type", type) // access - refresh
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret);

        if(role !=null) {
            builder.claim("role", role);
        }

        return builder.compact();
                
    }

    /**
     * 토큰에서 이메일(subject) 추출
     */
    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * 토큰에서 role 추출
     */
    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

       // 토큰 타입 확인 (access / refresh)
    public String getType(String token) {
        return getClaims(token).get("type", String.class);
    }

    /**
     * Refresh Token 만료 시간 꺼내기 (DB 저장용)
    */
    public LocalDateTime getExpiredAt(String token){
        Date expiration = getClaims(token).getExpiration();
        return expiration.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * 토큰 유효성 검증 (서명 + 만료 체크)
     */
    public boolean vaildateToken(String token) {
        try{
            getClaims(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    

    /**
     * JWT 파싱 후 Claims 반환
     * (토큰 검증 + payload 추출)
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
