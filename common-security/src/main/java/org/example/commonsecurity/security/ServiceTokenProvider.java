//package org.example.commonsecurity.security;
//
//// 서비스간 통신에 사용할 토큰 발급 유틸
//
//import org.example.commonsecurity.jwt.JwtTokenProvider;
//
//public class ServiceTokenProvider {
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public ServiceTokenProvider(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    public String createServiceToken(String serviceName) {
//        // sub = "service:meal" 이런 식으로
//        return jwtTokenProvider.createToken(serviceName);
//    }
//}
