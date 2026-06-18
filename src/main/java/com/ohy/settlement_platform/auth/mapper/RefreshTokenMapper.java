package com.ohy.settlement_platform.auth.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ohy.settlement_platform.auth.domain.RefreshToken;

@Mapper
public interface RefreshTokenMapper {
    /** refresh token 저장(로그인 할 때마다 갱신)*/
    void upsertRefreshToken(RefreshToken refreshToken);

    /** mail로 Refresh Token 조회 */
    Optional<RefreshToken> findByEmail(String email);

    /** 로그아웃 시 삭제 */
    void deleteByEmail(String email);
}
