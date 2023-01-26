package com.tamlog.blog.utils;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class RestDocsUtil {


    public static final List<FieldDescriptor> CATEGORY_REQUEST_FIELDS = new ArrayList<FieldDescriptor>(
            List.of(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 인덱스"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("카테고리 이름"),
                    fieldWithPath("priority").type(JsonFieldType.NUMBER).description("카테고리 순서")
            )
    );

    public static final List<FieldDescriptor> CATEGORY_RESPONSE_FIELDS = new ArrayList<FieldDescriptor>(
            List.of(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 인덱스"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("카테고리 이름"),
                    fieldWithPath("priority").type(JsonFieldType.NUMBER).description("카테고리 순서")
            )
    );

    public static final List<FieldDescriptor> ACCOUNT_RESPONSE_FIELDS = new ArrayList<FieldDescriptor>(
            List.of(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임")
            )
    );

    public static final List<FieldDescriptor> BOARD_SAVE_REQUEST_FIELDS = new ArrayList<FieldDescriptor>(
            List.of(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문"),
                    fieldWithPath("categoryTitle").type(JsonFieldType.STRING).description("카테고리 이름")
            )
    );

    public static final List<FieldDescriptor> BOARD_UPDATE_REQUEST_FIELDS = new ArrayList<FieldDescriptor>(
            List.of(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문")
                    )
    );

    public static final List<FieldDescriptor> BOARD_RESPONSE_FIELDS = new ArrayList<FieldDescriptor>(
            List.of(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 인덱스"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문"),
                    fieldWithPath("hit").type(JsonFieldType.NUMBER).description("게시글 조회수"),
                    fieldWithPath("like").type(JsonFieldType.NUMBER).description("게시글 좋아요수"),
                    fieldWithPath("createdAt").description("게시글 생성일"),
                    fieldWithPath("updatedAt").description("게시글 수정일"),
                    fieldWithPath("category").description("카테고리"),
                    fieldWithPath("category.id").type(JsonFieldType.NUMBER).description("카테고리 인덱스"),
                    fieldWithPath("category.title").type(JsonFieldType.STRING).description("카테고리 이름"),
                    fieldWithPath("category.priority").type(JsonFieldType.NUMBER).description("카테고리 순서"),
                    fieldWithPath("account").description("유저"),
                    fieldWithPath("account.email").type(JsonFieldType.STRING).description("유저 이메일"),
                    fieldWithPath("account.nickname").type(JsonFieldType.STRING).description("유저 닉네임")
            )
    );

    public static final List<FieldDescriptor> ERROR_RESPONSE_FIELDS = new ArrayList<>(
            List.of(
                fieldWithPath("timeStamp").description("게시글 수정일"),
                fieldWithPath("status").type(JsonFieldType.NUMBER).description("게시글 인덱스"),
                fieldWithPath("error").type(JsonFieldType.STRING).description("게시글 제목"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("게시글 본문")
            )
    );

    public static final String NO_TITLE = "제목에 내용이 없음";
    public static final String NO_CONTENT = "본문에 내용이 없음";
    public static final String NO_AUTHORIZED = "권한이 없음";
}
