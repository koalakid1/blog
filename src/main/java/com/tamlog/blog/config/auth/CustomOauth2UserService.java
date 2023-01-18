//package com.tamlog.blog.config.auth;
//
//import com.tamlog.blog.config.auth.dto.OAuthAttributes;
//import com.tamlog.blog.config.auth.dto.SessionUser;
//import com.tamlog.blog.domain.user.User;
//import com.tamlog.blog.domain.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpSession;
//import java.util.Collections;
//
//@RequiredArgsConstructor
//@Service
//public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//    private final UserRepository userRepository;
//    private final HttpSession httpSession;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        String registrationId = userRequest
//                .getClientRegistration()
//                .getRegistrationId(); // 어떤 sns를 연동했는지 정보
//
//        String userNameAttributeName = userRequest
//                .getClientRegistration()
//                .getProviderDetails()
//                .getUserInfoEndpoint()
//                .getUserNameAttributeName(); // 로그인 진행시 primary key
//
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,oAuth2User.getAttributes()); // OAuthService를 통해 가져온 OAuth2User의 attribute를 담는 클래스
//
//        User user = saveOrUpdate(attributes);
//
//        httpSession.setAttribute("user", new SessionUser(user)); // 세션에 사용자 정보를 저장하는 클래스
//
//        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey()
//                );
//    }
//
//    private User saveOrUpdate(OAuthAttributes attributes) {
//        User user = userRepository.findByEmail(attributes.getEmail())
//                .map(entity -> entity.update(attributes.getName(), attributes.getPath()))
//                .orElse(attributes.toEntity());
//
//        return userRepository.save(user);
//    }
//}
