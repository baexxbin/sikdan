package org.example.memberservice.application.impl;

import lombok.RequiredArgsConstructor;
import org.example.memberservice.application.MemberService;
import org.example.memberservice.model.vo.Member;
import org.example.memberservice.persistence.MemberMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public Member findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    @Override
    public Member findById(String id) {
        return memberMapper.findByEmail(id);
    }

    @Override
    public void registerMember(Member member) {
        memberMapper.insertMember(member);
    }
}
