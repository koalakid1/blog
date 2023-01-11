package com.tamlog.blog.controller;

import com.tamlog.blog.dto.CategoryDto;
import com.tamlog.blog.entity.Category;
import com.tamlog.blog.service.CategoryService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

class CategoryControllerTest extends BaseControllerTest {
    @MockBean
    private CategoryService categoryService;

    private List<Category> categories = new ArrayList<>();


    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            categories.add(new Category((long) i, "category" + i, i));
        }
    }


    @Test
    @DisplayName(value = "모든 카테고리 리스트 가져오는지 테스트")
    void getCategoriesTest() throws Exception {
        //given
        var responseBody = categories.stream()
                .map(CategoryDto.Response::of)
                .toList();


        var response = given(spec).filter(document(DEFAULT_RESTDOC_PATH, REQUEST_FIELDS, RESPONSE_FIELDS))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .log().all()
        .when().get("/api/categories")
        .then().statusCode(HttpStatus.OK.value());
        System.out.println("response = " + response);
    }

    private static final Snippet REQUEST_FIELDS = requestFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 인덱스"),
            fieldWithPath("name").type(JsonFieldType.NUMBER).description("카테고리 이름"),
            fieldWithPath("priority").type(JsonFieldType.STRING).description("카테고리 순서")
    );

    private static final Snippet RESPONSE_FIELDS = responseFields(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 인덱스"),
            fieldWithPath("name").type(JsonFieldType.NUMBER).description("카테고리 이름"),
            fieldWithPath("priority").type(JsonFieldType.STRING).description("카테고리 순서")
    );
}