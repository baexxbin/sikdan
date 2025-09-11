package org.example.memberservice.application;

import org.example.memberservice.model.vo.Member;

public interface MemberService {

    Member findByEmail(String email);

    Member findById(String id);

    void registerMember(Member member);
}
