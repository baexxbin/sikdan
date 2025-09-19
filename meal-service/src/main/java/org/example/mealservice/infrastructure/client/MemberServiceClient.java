package org.example.mealservice.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "member-service", path = "/api/member")
public interface MemberServiceClient {

    @GetMapping("/{memberId}/exists")
    Boolean existsById(@PathVariable("memberId") Long memberId);
}
