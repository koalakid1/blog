package com.tamlog.blog.domain.user;

import com.tamlog.blog.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "user")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", length = 40)
    private String email;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private Role role;

    @Column(name = "path", length = 200)
    private String path;

    @Builder
    public User(String email, String name, Role role, String path) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.path = path;
    }

    public User update(String name, String path) {
        this.name = name;
        this.path = path;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}