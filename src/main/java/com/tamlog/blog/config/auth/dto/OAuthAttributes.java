//package com.tamlog.blog.config.auth.dto;
//
//import com.tamlog.blog.domain.user.Role;
//import com.tamlog.blog.domain.user.User;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Map;
//
//@Getter
//public class OAuthAttributes {
//    private Map<String, Object> attributes;
//    private String nameAttributeKey;
//    private String name;
//    private String email;
//    private String path;
//
//    @Builder
//    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String path) {
//        this.attributes = attributes;
//        this.nameAttributeKey = nameAttributeKey;
//        this.name = name;
//        this.email = email;
//        this.path = path;
//    }
//
//    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//        return ofGoogle(userNameAttributeName, attributes);
//    }
//
//    private static OAuthAttributes ofGoogle(String userNameAttribute, Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .path((String) attributes.get("picture"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttribute)
//                .build();
//    }
//
//    public User toEntity() {
//        return User.builder()
//                .nickname(name)
//                .email(email)
//                .role(Role.GUEST)
//                .path(path)
//                .build();
//    }
//}
