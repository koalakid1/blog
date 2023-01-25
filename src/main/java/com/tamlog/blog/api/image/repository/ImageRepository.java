package com.tamlog.blog.api.image.repository;

import com.tamlog.blog.api.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}