package org.example.sikdan.auth.application;

import lombok.RequiredArgsConstructor;
import org.example.sikdan.auth.application.impl.CustomUserDetails;
import org.example.sikdan.domain.member.model.vo.Member;
import org.example.sikdan.domain.member.persistence.MemberMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberMapper.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
        }

        return new CustomUserDetails(member);
    }

}
