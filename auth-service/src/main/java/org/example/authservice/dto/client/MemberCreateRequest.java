package org.example.authservice.dto.client;

public record MemberCreateRequest(String memberName,
                                  String email,
                                  String password,
                                  String nickname) {
}
