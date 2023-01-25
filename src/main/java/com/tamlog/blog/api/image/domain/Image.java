package com.tamlog.blog.api.image.domain;

import com.tamlog.blog.api.board.domain.Board;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "path", nullable = false, length = 100)
    private String path;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}