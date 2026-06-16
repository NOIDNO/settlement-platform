package com.ohy.settlement_platform.member.mapper;

import java.util.Optional;

import com.ohy.settlement_platform.member.domain.Member;

public interface MemberMapper {
    /** 회원가입 */
    void insertMember(Member member);
    
    /** 회원정보 조회 */
    Optional<Member> FindByEmail(String email);
    
    /** 회원 존재 여부 확인 */
    boolean existsByEmail(String email);

}
