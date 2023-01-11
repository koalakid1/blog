package com.tamlog.blog.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", length = 40)
    private String email;

    @Column(name = "nickname", nullable = false, length = 15)
    private String nickname;

    @Column(name = "password", length = 40)
    private String password;

    @Column(name = "admin", nullable = false, length = 10)
    private String admin;

    @Column(name = "sns_id", length = 50)
    private String sns_id;

    @Column(name = "path", length = 200)
    private String path;
}