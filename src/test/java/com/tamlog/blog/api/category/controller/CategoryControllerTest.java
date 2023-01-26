package com.tamlog.blog.api.category.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.tamlog.blog.annotations.WithMockCustomUser;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.api.category.dto.CategoryRequest;
import com.tamlog.blog.utils.BaseControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.HashMap;
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

class CategoryControllerTest extends BaseControllerTest {

    private final Map<String, Object> postCategory = new HashMap<>();
    private final Map<String, Object> updateCategory = new HashMap<>();

    @BeforeEach
    void setUp() {
        for (int i = 1; i < 10; i++) {
            var category = new Category((long) i, "category" + i, i);
            categoryRepository.save(category);
        }
        updateCategory.put("id", 9l);
        postCategory.put("id", 10l);
        postCategory.put("title", "category10");
        postCategory.put("priority", 10);

        defaultResourceBuilder.tag("Category-API")
                .responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                );
    }


    @Test
    @WithMockCustomUser
    @DisplayName(value = "모든 카테고리 리스트 가져오기 성공")
    void findAllTest() throws Exception {
        // when
        var result = mockMvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        // then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("카테고리 목록 전체를 가져옵니다.")
                        .description("카테고리 목록 전체를 가져옵니다.")
                        .responseFields(applyPathPrefix("[]", CATEGORY_RESPONSE_FIELDS))
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "카테고리 제목이 비어서 실패")
    void saveTitleFailTest() throws Exception {
        // given
        CategoryRequest category = new CategoryRequest(10l, "", 10);

        // when
        var result = mockMvc.perform(post("/api/categories")
                .content(objectMapper.writeValueAsString(postCategory))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(CATEGORY_REQUEST_FIELDS)
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "카테고리 추가 성공")
    void saveTest() throws Exception {
        // given
        CategoryRequest category = new CategoryRequest(10l, "category10", 10);

        // when
        var result = mockMvc.perform(post("/api/categories")
                .content(objectMapper.writeValueAsString(postCategory))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .tag("Category-API")
                        .summary("카테고리를 새로 등록합니다.")
                        .description("카테고리를 새로 등록합니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .requestFields(CATEGORY_REQUEST_FIELDS)
                        .responseFields(CATEGORY_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "타이틀만 데이터로 들어왔을 때, 타이 정보만 업데이트 되는지 테스트")
    void updateCategoryTitleTest() throws Exception {
        //given
        HashMap<String, Object> request = new HashMap<>();
        request.put("title", "category11-update");

        //when
        var result = mockMvc.perform(patch("/api/categories/{category_id}/title", 9l)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title", Matchers.is(request.get("title"))))
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .tag("Category-API")
                        .summary("카테고리를 업데이트합니다.")
                        .description("카테고리를 업데이트합니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .requestFields(fieldWithPath("title")
                                .type(JsonFieldType.STRING)
                                .description("카테고리 이름"))
                        .responseFields(CATEGORY_RESPONSE_FIELDS)
                        .build())));

        System.out.println(categoryRepository.findById(9l).get().getTitle());
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "updatePriority가 주어지면 priority 순서대로 수정")
    void updateCategoryPriorityTest() throws Exception {
        HashMap<String, Object> request = new HashMap<>();
        request.put("priority", 5);
        //when
        var result = mockMvc.perform(patch("/api/categories/{category_id}/priority", 9l)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.priority", Matchers.is(5)))
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .tag("Category-API")
                        .summary("카테고리를 업데이트합니다.")
                        .description("카테고리를 업데이트합니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .requestFields(fieldWithPath("priority")
                                .type(JsonFieldType.NUMBER)
                                .description("카테고리 순서"))
                        .responseFields(CATEGORY_RESPONSE_FIELDS)
                        .build())));
    }
}