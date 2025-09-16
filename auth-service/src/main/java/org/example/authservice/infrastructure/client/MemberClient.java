package org.example.authservice.infrastructure.client;

import org.example.authservice.dto.client.MemberCreateRequest;
import org.example.common.dto.response.MemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "member-service", path = "/api/members")
public interface MemberClient {

    @PostMapping("/register")
    void registerMember(@RequestBody MemberCreateRequest request);

    @GetMapping("/email/{email}")
    MemberResponse findByEmail(@PathVariable("email") String email);

    @GetMapping("/{id}")
    MemberResponse findById(@PathVariable("id") Long id);
}
