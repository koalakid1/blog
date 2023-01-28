package com.tamlog.blog.utils;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamlog.blog.api.account.dto.AccountRequest;
import com.tamlog.blog.api.account.repository.AccountRepository;
import com.tamlog.blog.api.auth.dto.TokenResponse;
import com.tamlog.blog.api.auth.service.AuthService;
import com.tamlog.blog.api.board.repository.BoardRepository;
import com.tamlog.blog.api.category.repository.CategoryRepository;
import com.tamlog.blog.support.jwt.TokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("test")
public class BaseControllerTest {
    protected static final String DEFAULT_RESTDOCS_PATH = "{class_name}/{method_name}/";

    protected MockMvc mockMvc;
    protected ResourceSnippetParametersBuilder defaultResourceBuilder = ResourceSnippetParameters.builder();

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AccountRepository accountRepository;
    @Autowired
    protected BoardRepository boardRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManagerBuilder managerBuilder;
    @Autowired
    private TokenProvider tokenProvider;
    protected String accessToken;



    @BeforeEach
    void setUp(@Autowired WebApplicationContext applicationContext, RestDocumentationContextProvider restDocumentation) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation))
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                .alwaysDo(print())
                .build();
    }

    @BeforeEach
    void jwt() {
        AccountRequest request = new AccountRequest("test@test.com","abcD1234!", "test", "");
        authService.signup(request);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());
        Authentication authenticate = managerBuilder.getObject().authenticate(authenticationToken);
        TokenResponse tokenResponse = tokenProvider.generateAccessToken(authenticate);
        accessToken = tokenResponse.getAccessToken();
    }

    @AfterEach
    void delete() {
        boardRepository.deleteAll();
        categoryRepository.deleteAll();
        accountRepository.deleteAll();
    }
}
