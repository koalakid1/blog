package com.tamlog.blog.api.board.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.tamlog.blog.annotations.WithMockCustomUser;
import com.tamlog.blog.api.account.domain.Account;
import com.tamlog.blog.api.account.domain.Role;
import com.tamlog.blog.api.board.domain.Board;
import com.tamlog.blog.api.board.dto.BoardSaveRequest;
import com.tamlog.blog.api.board.dto.BoardUpdateReqeust;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.api.category.repository.CategoryRepository;
import com.tamlog.blog.utils.BaseControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.tamlog.blog.utils.FieldsUtils.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.applyPathPrefix;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardControllerTest extends BaseControllerTest {

    @Autowired
    private CategoryRepository categoryRepository;

    Account account;
    Category category1, category2;

    List<Board> boards = new ArrayList<>();


    @BeforeEach
    void setup() {
        category1 = categoryRepository.save(new Category(1l, "category1", 1));
        category2 = categoryRepository.save(new Category(2l, "category2", 2));
        account = accountRepository.save(new Account(1l, "test@test.com", "", "test", Role.USER, ""));
        for (int i = 1; i < 3; i++) {
            boards.add(boardRepository.save(new Board((long) i, "title" + i, "content" + i,0,0,category1, account)));
        }
    }

    @AfterEach
    void doneBoard() {
        boards.clear();
    }

    @Test
    @DisplayName(value = "id로 게시글 하나 가져오는지 테스트")
    @WithMockCustomUser
    void findByIdTest() throws Exception {
        // given
        long id = 1l;

        // when
        var result = mockMvc.perform(get("/api/boards/{boardId}", boards.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        // then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .tag("Board-API")
                        .summary("게시글 하나를 가져옵니다.")
                        .description("게시글 하나를 가져옵니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .responseFields(BOARD_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @DisplayName(value = "모든 게시글을 가져오는지 테스트")
    @WithMockCustomUser
    void findAllTest() throws Exception {
        // given
        // when
        var result = mockMvc.perform(get("/api/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        // then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .tag("Board-API")
                        .summary("게시글 전체를 가져옵니다.")
                        .description("게시글 전체를 가져옵니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .responseFields(applyPathPrefix("[]", BOARD_RESPONSE_FIELDS))
                        .build())));
    }


    @Test
    @DisplayName(value = "게시글 추가 성공")
    void saveTest() throws Exception {
        // given
        BoardSaveRequest request = new BoardSaveRequest("newTitle", "newContent", "category1");

        // when
        var result = mockMvc.perform(post("/api/boards")
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .tag("Board-API")
                        .summary("게시글을 새로 등록합니다.")
                        .description("게시글을 새로 등록합니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .requestFields(BOARD_SAVE_REQUEST_FIELDS)
                        .responseFields(BOARD_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @DisplayName(value = "게시글 업데이트 테스트")
    void updateTest() throws Exception {
        //given
        BoardUpdateReqeust request = new BoardUpdateReqeust("updateTitle", "updateContent");

        //when
        var result = mockMvc.perform(put("/api/boards/{boardId}", boards.get(0).getId())
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title", Matchers.is(request.getTitle())))
                .andExpect(jsonPath("$.content", Matchers.is(request.getContent())))
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .tag("Board-API")
                        .summary("게시글을 업데이트합니다.")
                        .description("게시글을 업데이트합니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .requestFields(BOARD_UPDATE_REQUEST_FIELDS)
                        .responseFields(BOARD_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @DisplayName(value = "게시글 카테고리 변경 테스트")
    void updateCategoryTest() throws Exception {
        //given
        Map<String, Long> request = new HashMap<>();
        request.put("categoryId", category2.getId());

        //when
        var result = mockMvc.perform(put("/api/boards/{boardId}/category", boards.get(0).getId())
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.category.id", Matchers.is(request.get("categoryId").intValue())))
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .tag("Board-API")
                        .summary("게시글의 카테고리를 업데이트합니다.")
                        .description("게시글의 카테고리를 업데이트합니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .requestFields(fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("바꿀 카테고리 번호"))
                        .responseFields(BOARD_RESPONSE_FIELDS)
                        .build())));
    }
}