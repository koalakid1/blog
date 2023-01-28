package com.tamlog.blog.utils;

import com.tamlog.blog.api.account.repository.AccountRepository;
import com.tamlog.blog.api.board.repository.BoardRepository;
import com.tamlog.blog.api.category.repository.CategoryRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BaseServiceTest {
    @MockBean
    protected CategoryRepository categoryRepository;

    @MockBean
    protected BoardRepository boardRepository;

    @MockBean
    protected AccountRepository accountRepository;
}
