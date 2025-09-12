package org.example.memberservice.presentation.api;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.response.MemberResponse;
import org.example.memberservice.application.MemberService;
import org.example.memberservice.dto.request.MemberCreateRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원 생성 (auth-service에서 호출)
    @PostMapping
    public MemberResponse createMember(@RequestBody MemberCreateRequest requestDto) {
        return memberService.registerMember(requestDto);
    }


    // 이메일로 회원 조회 (auth-service에서 로그인 검증용으로 호출)
    @GetMapping("/email/{email}")
    public MemberResponse findByEmail(@PathVariable String email) {
        return memberService.findByEmail(email);
    }


    // ID로 회원 조회
    @GetMapping("/{id}")
    public MemberResponse findById(@PathVariable Long id) {
        return memberService.findById(id);
    }
}
