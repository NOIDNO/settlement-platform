package com.ohy.settlement_platform.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohy.settlement_platform.auth.dto.LoginRequest;
import com.ohy.settlement_platform.auth.dto.LoginResponse;
import com.ohy.settlement_platform.auth.dto.ReissueRequest;
import com.ohy.settlement_platform.auth.dto.ReissueResponse;
import com.ohy.settlement_platform.auth.dto.SignupRequest;
import com.ohy.settlement_platform.auth.service.AuthService;
import com.ohy.settlement_platform.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthContoller {

    private final AuthService authService;


    /***
     * 회원가입
     * @param SignupRequest reques
     * @return
    */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signup (@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok(ApiResponse.ok("회원가입이 완료되었습니다.", null));
    }


    /***
     * 로그인
     * @param LoginRequest request
     * @return
    */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login (@Valid @RequestBody LoginRequest request) {
       LoginResponse reponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok(reponse));
    }
    
    
    /***
     * access token 재발급
     * @param ReissueRequest reques
     * @return
    */
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<ReissueResponse>> reissue (@Valid @RequestBody ReissueRequest request) {
       ReissueResponse reponse = authService.reissue(request);
        return ResponseEntity.ok(ApiResponse.ok(reponse));
    }
    
    /***
     * 로그아웃
     * @param ReissueRequest reques
     * @return
    */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout (Principal principal) {
       authService.logout(principal.getName());;
        return ResponseEntity.ok(ApiResponse.ok("로그아웃되었습니다.", null));
    }
    
}
