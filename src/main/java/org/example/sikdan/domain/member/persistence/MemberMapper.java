package org.example.sikdan.domain.member.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.example.sikdan.domain.member.model.vo.Member;

import java.util.List;

@Mapper
public interface MemberMapper {

    void insertMember(Member member);

    Member findByMemberId(Long memberId);

    Member findByEmail(String email);

    List<Member> findAllMembers();
}
