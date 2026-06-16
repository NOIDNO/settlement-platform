package com.ohy.settlement_platform.auth.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ohy.settlement_platform.auth.dto.SignupRequest;
import com.ohy.settlement_platform.auth.mapper.RefreshTokenMapper;
import com.ohy.settlement_platform.common.exception.CustomException;
import com.ohy.settlement_platform.common.exception.ErrorCode;
import com.ohy.settlement_platform.common.jwt.JwtUtil;
import com.ohy.settlement_platform.member.domain.Member;
import com.ohy.settlement_platform.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberMapper memberMapper;
    private final RefreshTokenMapper refreshTokenMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public void signup(SignupRequest request){
        //회원가입 여부 확인
        if(memberMapper.existsByEmail(request.getEmail())){ 
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL); //이미 사용 중인 이메일입니다
        }

        //가입한 회원 정보 세팅
        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(request.getRole())
                .build();

        //회원정보 저장
        memberMapper.insertMember(member);

    }

}
