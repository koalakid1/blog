package com.tamlog.blog.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamlog.blog.annotations.WithMockCustomUser;
import com.tamlog.blog.api.dto.CategoryDto;
import com.tamlog.blog.domain.board.Category;
import com.tamlog.blog.domain.board.CategoryRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.applyPathPrefix;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends BaseControllerTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
        postCategory.put("name", "category10");
        postCategory.put("priority", 10);
    }


    @Test
    @WithMockCustomUser
    @DisplayName(value = "모든 카테고리 리스트 가져오는지 테스트")
    void getCategoriesTest() throws Exception {
        // when
        var result = mockMvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        // then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .summary("카테고리 목록 전체를 가져옵니다.")
                        .description("카테고리 목록 전체를 가져옵니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .responseFields(applyPathPrefix("[]", RESPONSE_FIELDS))
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "카테고리 추가 성공")
    void postCategoryTest() throws Exception {
        // given
        CategoryDto.Request category = new CategoryDto.Request(10l, "category10", 10);

        // when
        var result = mockMvc.perform(post("/api/categories")
                .content(objectMapper.writeValueAsString(postCategory))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .summary("카테고리를 새로 등록합니다.")
                        .description("카테고리를 새로 등록합니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .responseFields(RESPONSE_FIELDS)
                        .build())));
    }

    @Test
    @WithMockCustomUser
    @DisplayName(value = "이름만 데이터로 들어왔을 때, 이름 정보만 업데이트 되는지 테스트")
    void updateCategoryNameTest() throws Exception {
        //given
        HashMap<String, Object> request = new HashMap<>();
        request.put("name", "category11-update");

        //when
        var result = mockMvc.perform(patch("/api/categories/{category_id}/name", 9l)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then

        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", Matchers.is(request.get("name"))))
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(ResourceSnippetParameters.builder()
                        .summary("카테고리를 업데이트합니다.")
                        .description("카테고리를 업데이트합니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .requestFields(fieldWithPath("name")
                                .type(JsonFieldType.STRING)
                                .description("카테고리 이름"))
                        .responseFields(RESPONSE_FIELDS)
                        .build())));

        System.out.println(categoryRepository.findById(9l).get().getName());
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
                        .summary("카테고리를 업데이트합니다.")
                        .description("카테고리를 업데이트합니다.")
                        .responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("응답 방식")
                        )
                        .requestFields(fieldWithPath("priority")
                                .type(JsonFieldType.NUMBER)
                                .description("카테고리 순서"))
                        .responseFields(RESPONSE_FIELDS)
                        .build())));
    }

    private static final List<FieldDescriptor> REQUEST_FIELDS = new ArrayList<FieldDescriptor>(
            List.of(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 인덱스"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("카테고리 이름"),
                    fieldWithPath("priority").type(JsonFieldType.NUMBER).description("카테고리 순서")
            )
    );

    private static final List<FieldDescriptor> RESPONSE_FIELDS = new ArrayList<FieldDescriptor>(
            List.of(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 인덱스"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("카테고리 이름"),
                    fieldWithPath("priority").type(JsonFieldType.NUMBER).description("카테고리 순서")
            )
    );
}