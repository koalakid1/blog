package com.tamlog.blog.domain.user;

import com.tamlog.blog.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "user", schema = "public")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", length = 40)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, length = 15)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private Role role;

    @Column(name = "path", length = 200)
    private String path;

    @Builder
    public User(Long id, String email, String password, String nickname, Role role, String path) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.path = path;
    }

    public User update(String nickname, String path) {
        this.nickname = nickname;
        this.path = path;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}