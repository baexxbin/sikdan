package org.example.memberservice.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.example.memberservice.model.vo.Member;

import java.util.List;

@Mapper
public interface MemberMapper {

    void insertMember(Member member);

    Member findByMemberId(Long memberId);

    Member findByEmail(String email);

    List<Member> findAllMembers();
}
