package com.tamlog.blog.api.account.domain;

import com.tamlog.blog.support.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "account")
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Nickname nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private Role role;

    @Column(name = "path", length = 200)
    private String path;

    protected Account() {
    }

    @Builder
    public Account(Long id, String email, String password, String nickname, Role role, String path) {
        this.id = id;
        this.email = new Email(email);
        this.password = new Password(password);
        this.nickname = new Nickname(nickname);
        this.role = role;
        this.path = path;
    }

    public Account update(String nickname, String path) {
        this.nickname = new Nickname(nickname);
        this.path = path;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void updateNickname(String nickname) {
        this.nickname = new Nickname(nickname);
    }

    public void updatePassword(String nowPassword, String newPassword, PasswordEncoder passwordEncoder) {
        this.password.updatePassword(nowPassword, newPassword, passwordEncoder);
    }
}