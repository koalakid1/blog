package com.tamlog.blog.api.board.controller;

import com.tamlog.blog.annotations.WithMockCustomUser;
import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.account.domain.Email;
import com.tamlog.blog.api.account.domain.Role;
import com.tamlog.blog.api.board.domain.Board;
import com.tamlog.blog.api.board.dto.BoardRequest;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.utils.BaseControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.tamlog.blog.utils.RestDocsUtil.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.applyPathPrefix;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardControllerTest extends BaseControllerTest {
    Account account1, account2;
    Category category1, category2;
    List<Board> boards = new ArrayList<>();


    @BeforeEach
    void setUp() {
        category1 = categoryRepository.save(new Category(1l, "category1", 1));
        category2 = categoryRepository.save(new Category(2l, "category2", 2));
        account1 = accountRepository.findByEmail(new Email("test@test.com")).get();
        account2 = accountRepository.save(new Account(100l, "test2@test.com", "", "test2", Role.USER, ""));

        for (int i = 1; i < 3; i++) {
            boards.add(boardRepository.save(new Board("title" + i, "content" + i,category1, account1, new ArrayList<>())));
        }
        boards.add(boardRepository.save(new Board("title" + 3, "content" + 3,category1, account2, new ArrayList<>())));

        defaultResourceBuilder.tag("Board-API")
                .responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                );
    }

    @AfterEach
    void clear() {
        boards.clear();
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "id로 게시글 하나 가져오기 성공")
    void findByIdTest() throws Exception {
        // given
        long id = boards.get(0).getId();

        // when
        var result = mockMvc.perform(get("/api/boards/{boardId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        // then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("게시글 하나를 가져옵니다.")
                        .description("게시글 하나를 가져옵니다.")
                        .responseFields(BOARD_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "id로 게시글 하나 가져오기 실패")
    void findByIdFailTest() throws Exception {
        // given
        long id = 100l;

        // when
        var result = mockMvc.perform(get("/api/boards/{boardId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        // then
        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "모든 게시글을 가져오기 성공")
    void findAllTest() throws Exception {
        // given
        // when
        var result = mockMvc.perform(get("/api/categories/{categoryId}/boards", category1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        // then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("게시글 전체를 가져옵니다.")
                        .description("게시글 전체를 가져옵니다.")
                        .responseFields(applyPathPrefix("[]", BOARD_RESPONSE_FIELDS))
                        .build())));
    }


    @Test
    @WithMockCustomUser
    @DisplayName(value = "게시글 추가 성공")
    void saveTest() throws Exception {
        // given
        BoardRequest request = new BoardRequest("newTitle", "newContent");

        // when
        var result = mockMvc.perform(post("/api/categories/{categoryId}/boards", category1.getId())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("게시글을 새로 등록합니다.")
                        .description("게시글을 새로 등록합니다.")
                        .requestFields(BOARD_REQUEST_FIELDS)
                        .responseFields(BOARD_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "게시글 제목이 없어서 추가 실패")
    void saveTitleFailTest() throws Exception {
        // given
        BoardRequest request = new BoardRequest("", "newContent");

        // when
        var result = mockMvc.perform(post("/api/categories/{categoryId}/boards", category1.getId())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(BOARD_REQUEST_FIELDS)
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "게시글 본문이 없어서 추가 실패")
    void saveContentFailTest() throws Exception {
        // given
        BoardRequest request = new BoardRequest("newTitle", "");

        // when
        var result = mockMvc.perform(post("/api/categories/{categoryId}/boards", category1.getId())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(BOARD_REQUEST_FIELDS)
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "게시글 업데이트 성공")
    void updateTest() throws Exception {
        //given
        BoardRequest request = new BoardRequest("updateTitle", "updateContent");

        //when
        var result = mockMvc.perform(put("/api/boards/{boardId}", boards.get(0).getId())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title", Matchers.is(request.getTitle())))
                .andExpect(jsonPath("$.content", Matchers.is(request.getContent())))
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("게시글을 업데이트합니다.")
                        .description("게시글을 업데이트합니다.")
                        .requestFields(BOARD_REQUEST_FIELDS)
                        .responseFields(BOARD_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "게시글 제목이 없어서 업데이트 실패")
    void updateTitleFailTest() throws Exception {
        //given
        BoardRequest request = new BoardRequest("", "updateContent");

        //when
        var result = mockMvc.perform(put("/api/boards/{boardId}", boards.get(0).getId())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(BOARD_REQUEST_FIELDS)
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "게시글 본문이 없어서 업데이트 실패")
    void updateContentFailTest() throws Exception {
        //given
        BoardRequest request = new BoardRequest("updateTitle", "");

        //when
        var result = mockMvc.perform(put("/api/boards/{boardId}", boards.get(0).getId())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(BOARD_REQUEST_FIELDS)
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }


    @Test
    @WithMockCustomUser
    @DisplayName(value = "게시글 권한이 없어서 업데이트 실패")
    void updateAuthenticateFailTest() throws Exception {
        //given
        BoardRequest request = new BoardRequest("updateTitle", "updateContent");

        //when
        var result = mockMvc.perform(put("/api/boards/{boardId}", boards.get(boards.size()-1).getId())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(BOARD_REQUEST_FIELDS)
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }


    @Test
    @WithMockCustomUser
    @DisplayName(value = "게시글 카테고리 업데이트 성공")
    void updateCategoryTest() throws Exception {
        //given
        Map<String, Long> request = new HashMap<>();
        request.put("categoryId", category2.getId());

        //when
        var result = mockMvc.perform(put("/api/boards/{boardId}/category", boards.get(0).getId())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.category.id", Matchers.is(request.get("categoryId").intValue())))
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("게시글의 카테고리를 업데이트합니다.")
                        .description("게시글의 카테고리를 업데이트합니다.")
                        .requestFields(fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("바꿀 카테고리 번호"))
                        .responseFields(BOARD_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "게시글 카테고리가 존재하지 않아서 업데이트 실패")
    void updateCategoryFailTest() throws Exception {
        //given
        Map<String, Long> request = new HashMap<>();
        request.put("categoryId", 5l);

        //when
        var result = mockMvc.perform(put("/api/boards/{boardId}/category", boards.get(0).getId())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("바꿀 카테고리 번호"))
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }
}