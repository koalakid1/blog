package com.tamlog.blog.api.reply.repository;

import com.tamlog.blog.api.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}