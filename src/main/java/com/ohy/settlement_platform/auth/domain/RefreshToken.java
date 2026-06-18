package com.ohy.settlement_platform.auth.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



/**
 * Jrefresh_token 테이블과 매핑되는 도메인 클래스
 *
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    private Long tokenId;
    private String email;
    private String token;
    private LocalDateTime expirtedAt;
    private LocalDateTime createdAt;
}
