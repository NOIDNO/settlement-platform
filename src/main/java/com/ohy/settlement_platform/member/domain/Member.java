package com.ohy.settlement_platform.member.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long memberId;
    private String email;
    private String password;
    private String name;
    private String role;
    private LocalDateTime createdAt;

}
