package org.example.sikdan.domain.member.application;

import org.example.sikdan.domain.member.model.vo.Member;

public interface MemberService {

    Member findByEmail(String email);

    Member findById(String id);

    void registerMember(Member member);
}
