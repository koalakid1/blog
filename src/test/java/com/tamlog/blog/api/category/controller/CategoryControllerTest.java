package com.tamlog.blog.api.category.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.tamlog.blog.api.category.domain.Category;
import com.tamlog.blog.api.category.dto.CategoryRequest;
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

class CategoryControllerTest extends BaseControllerTest {

    private final List<Category> categories = new ArrayList<>();
    @BeforeEach
    void setUp() {
        for (int i = 1; i < 10; i++) {
            var category = new Category((long) i, "category" + i, i);
            Category saveCategory = categoryRepository.save(category);
            categories.add(saveCategory);
        }

        defaultResourceBuilder.tag("Category-API");
    }

    @AfterEach
    void clear() {
        categories.clear();
    }


    @Test
    @DisplayName(value = "모든 카테고리 리스트 가져오기 성공")
    void findAllTest() throws Exception {
        // when
        var result = mockMvc.perform(get("/api/categories")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
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
    @DisplayName(value = "카테고리 추가 성공")
    void saveTest() throws Exception {
        //given
        CategoryRequest saveCategoryRequest = new CategoryRequest("category10", 10);

        // when
        var result = mockMvc.perform(post("/api/categories")
                .content(objectMapper.writeValueAsString(saveCategoryRequest))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
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
    @DisplayName(value = "카테고리 제목이 비어서 실패")
    void saveTitleFailTest() throws Exception {
        // given
        CategoryRequest saveCategoryRequest = new CategoryRequest("", 10);

        // when
        var result = mockMvc.perform(post("/api/categories")
                .content(objectMapper.writeValueAsString(saveCategoryRequest))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
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
    @DisplayName(value = "타이틀만 데이터로 들어왔을 때, 타이틀 정보만 업데이트 성공")
    void updateCategoryTitleTest() throws Exception {
        //given
        HashMap<String, Object> request = new HashMap<>();
        request.put("title", "category11-update");
        Integer index = categories.size() - 1;
        Long id = categories.get(index).getId();

        //when
        var result = mockMvc.perform(patch("/api/categories/{category_id}/title", id)
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title", Matchers.is(request.get("title"))))
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("카테고리 제목을 업데이트합니다.")
                        .description("카테고리 제목을 업데이트합니다.")
                        .requestFields(fieldWithPath("title")
                                .type(JsonFieldType.STRING)
                                .description("카테고리 이름"))
                        .responseFields(CATEGORY_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @DisplayName(value = "updatePriority가 주어지면 priority 순서대로 수정 성공")
    void updateCategoryPriorityTest() throws Exception {
        //given
        Map<String, Object> updateCategoryRequest = new HashMap<>();
        Integer index = categories.size() - 1;
        Integer updatePriority = categories.get(index - 5).getPriority();
        updateCategoryRequest.put("priority", updatePriority);
        Long id = categories.get(index).getId();

        //when
        var result = mockMvc.perform(patch("/api/categories/{category_id}/priority", id)
                .content(objectMapper.writeValueAsString(updateCategoryRequest))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.priority", Matchers.is(updatePriority)))
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("카테고리 순서를 업데이트합니다.")
                        .description("카테고리 순서를 업데이트합니다.")
                        .requestFields(fieldWithPath("priority")
                                .type(JsonFieldType.NUMBER)
                                .description("카테고리 순서"))
                        .responseFields(CATEGORY_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @DisplayName(value = "카테고리가 존재하지 않아서 순서 수정 실패")
    void updateCategoryPriorityFailTest() throws Exception {
        //given
        Map<String, Object> updateCategoryRequest = new HashMap<>();
        updateCategoryRequest.put("priority", 5);
        Long id = categories.get(0).getId() - 1;

        //when
        var result = mockMvc.perform(patch("/api/categories/{category_id}/priority", id)
                .content(objectMapper.writeValueAsString(updateCategoryRequest))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(fieldWithPath("priority")
                                .type(JsonFieldType.NUMBER)
                                .description("카테고리 순서"))
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @DisplayName(value = "카테고리 삭제 성공")
    void deleteTest() throws Exception {
        //given
        Long id = categories.get(0).getId();

        //when
        var result = mockMvc.perform(delete("/api/categories/{category_id}", id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken));

        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("카테고리를 삭제합니다.")
                        .description("카테고리를 삭제합니다.")
                        .build())));
    }
}