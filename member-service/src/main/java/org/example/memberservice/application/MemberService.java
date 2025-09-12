package org.example.memberservice.application;

import org.example.common.dto.response.MemberResponse;
import org.example.memberservice.dto.request.MemberCreateRequest;

public interface MemberService {

    MemberResponse findByEmail(String email);

    MemberResponse findById(Long id);

    MemberResponse registerMember(MemberCreateRequest member);
}
