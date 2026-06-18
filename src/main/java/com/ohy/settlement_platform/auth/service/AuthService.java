package com.ohy.settlement_platform.auth.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ohy.settlement_platform.auth.domain.RefreshToken;
import com.ohy.settlement_platform.auth.dto.LoginRequest;
import com.ohy.settlement_platform.auth.dto.LoginResponse;
import com.ohy.settlement_platform.auth.dto.ReissueRequest;
import com.ohy.settlement_platform.auth.dto.ReissueResponse;
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

    /**
     * 로그인
     **/
    public LoginResponse login(LoginRequest requset){
        //가입 여부 확인
        Member member = memberMapper.findByEmail(requset.getEmail()).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND)); //회원을 찾을 수 없습니다.

        //비밀번호 검증
        if(!passwordEncoder.matches(requset.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        //access token 발급 : 30분
        String accessToken = jwtUtil.generateAccessToken(member.getEmail(), member.getRole());

        //refresh token 발급 : 7일
        String refreshToken = jwtUtil.generateRefreshToken(accessToken);
        refreshTokenMapper.upsertRefreshToken(
            RefreshToken.builder()
                    .email(member.getEmail())
                    .token(refreshToken)
                    .expirtedAt(jwtUtil.getExpiredAt(refreshToken))
                    .build()
        );
        return new LoginResponse(accessToken, refreshToken, member.getName(), member.getRole());
    }

    /**
     *  Access token 재발급 
     **/
    public ReissueResponse reissue(ReissueRequest request) {
        String refreshToken = request.getRefreshToken();

        //refresh token 유효성 검증
        if(jwtUtil.vaildateToken(refreshToken)){
            throw new CustomException(ErrorCode.INVALID_API_KEY);
        }

        //refresh token 타입 확인 -> access 토큰으로 재발급 요청 막기
        if(!"refresh".equals(jwtUtil.getType(refreshToken))){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        //서버에 저장된 refresh token 일치 확인
        String email = jwtUtil.getEmail(refreshToken);
        RefreshToken savedToken = refreshTokenMapper.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.INVALID_TOKEN));

        if(!savedToken.getToken().equals(refreshToken)){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        //새 access token 발급
        Member member = memberMapper.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        String newAccessToken = jwtUtil.generateAccessToken(member.getEmail(), member.getRole());
        return new ReissueResponse(newAccessToken);
    }

    /**
     *  로그아웃
     **/
    public void logout(String email) {
        refreshTokenMapper.deleteByEmail(email);
    }
}
