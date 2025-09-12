package org.example.memberservice.application.impl;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.response.MemberResponse;
import org.example.memberservice.application.MemberService;
import org.example.memberservice.dto.request.MemberCreateRequest;
import org.example.memberservice.model.vo.Member;
import org.example.memberservice.persistence.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public MemberResponse findByEmail(String email) {
        Member member = memberMapper.findByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("이메일 없음");
        }

        return new MemberResponse(
                member.getMemberId(),
                member.getMemberName(),
                member.getEmail(),
                member.getPassword(), // auth에서 password 검증해야 하므로 포함
                List.of("ROLE_USER")
        );
    }

    @Override
    public MemberResponse findById(Long id) {
        Member member = memberMapper.findByMemberId(id);

        if (member == null) {
            throw new IllegalArgumentException("이메일 없음");
        }

        return new MemberResponse(
                member.getMemberId(),
                member.getMemberName(),
                member.getEmail(),
                member.getPassword(), // auth에서 password 검증해야 하므로 포함
                List.of("ROLE_USER")
        );
    }

    @Override
    public MemberResponse registerMember(MemberCreateRequest request) {
        Member member = Member.builder()
                .memberName(request.memberName())
                .email(request.email())
                .password(request.password())   // 이미 auth-service에서 해싱되어 들어옴
                .nickname(request.nickname())
                .build();

        memberMapper.insertMember(member);

        return new MemberResponse(
                member.getMemberId(),
                member.getMemberName(),
                member.getEmail(),
                member.getPassword(),
                List.of("ROLE_USER")
        );
    }
}
