package com.tamlog.blog.api.account.controller;

import com.tamlog.blog.api.account.dto.AccountChangePasswordRequest;
import com.tamlog.blog.utils.BaseControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.HashMap;
import java.util.Map;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.tamlog.blog.utils.RestDocsUtil.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends BaseControllerTest {
    @BeforeEach
    void setUp() {
        defaultResourceBuilder.tag("Account-API");
    }

    @Test
    @DisplayName(value = "현재 로그인한 회원 정보 가져오기 성공")
    void findOneTest() throws Exception {
        var result = mockMvc.perform(get("/api/account/profile")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("현재 유저 정보를 가져옵니다.")
                        .description("현재 유저 정보를 가져옵니다.")
                        .responseFields(ACCOUNT_RESPONSE_FIELDS)
                        .build())));
    }

    @DisplayName(value = "회원 닉네임 변경 성공")
    @ParameterizedTest
    @ValueSource(strings = {"newNickname", "nicknamezz", "koalakid", "kaoalkid154"})
    void updateNicknameTest(String nickname) throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("nickname", nickname);

        var result = mockMvc.perform(put("/api/account/nickname")
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.nickname", Matchers.is(nickname)))
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("회원 닉네임을 변경합니다.")
                        .description("회원 닉네임을 변경합니다.")
                        .requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임")
                        )
                        .responseFields(ACCOUNT_RESPONSE_FIELDS)
                        .build())));
    }

    @DisplayName("변경 할 닉네임 형식이 올바르지 않아서 오류")
    @ParameterizedTest
    @ValueSource(strings = {"nickname!@", "!#%!#%", "", " "})
    void updateNicknameInvalidTest(String nickname) throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("nickname", nickname);

        var result = mockMvc.perform(put("/api/account/nickname")
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임")
                        )
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }

    @DisplayName(value = "회원 비밀번호 변경 성공")
    @ParameterizedTest
    @ValueSource(strings = {"p@ssW0rd#", "Aa1@Bb2#Cc3$Dd4%", "Str0ngPa$$word", "P@55w0Rd"})
    void updatePasswordTest(String newPassword) throws Exception {
        String currentPassword = "abcD1234!";
        AccountChangePasswordRequest request = new AccountChangePasswordRequest(currentPassword, newPassword);

        var result = mockMvc.perform(put("/api/account/password")
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().is2xxSuccessful())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .summary("회원 비밀번호를 변경합니다.")
                        .description("회원 비밀번호를 변경합니다.")
                        .requestFields(ACCOUNT_CHANGE_PASSWORD_REQUEST_FIELDS)
                        .responseFields(ACCOUNT_RESPONSE_FIELDS)
                        .build())));
    }

    @DisplayName(value = "현재 비밀번호가 맞지 않는 오류")
    @ParameterizedTest
    @ValueSource(strings = {"p@ssW0rd#", "Aa1@Bb2#Cc3$Dd4%", "Str0ngPa$$word", "P@55w0Rd"})
    void updatePasswordNotMatchedTest(String newPassword) throws Exception {
        String currentPassword = "abcD1234!@";
        AccountChangePasswordRequest request = new AccountChangePasswordRequest(currentPassword, newPassword);

        var result = mockMvc.perform(put("/api/account/password")
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(ACCOUNT_CHANGE_PASSWORD_REQUEST_FIELDS)
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }



    @DisplayName(value = "바꿀 비밀번호의 형식이 맞지 않아 오류")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "abcdAbcd", "abCd1234", "12341234", "abcd12!", "abcd1234^",
            "123456789012345678901", "123456789abcdABCD!@#$%"})
    void updatePasswordInvalidFormatTest(String newPassword) throws Exception {
        String currentPassword = "abcD1234!";
        AccountChangePasswordRequest request = new AccountChangePasswordRequest(currentPassword, newPassword);

        var result = mockMvc.perform(put("/api/account/password")
                .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().is4xxClientError())
                .andDo(document(DEFAULT_RESTDOCS_PATH, resource(defaultResourceBuilder
                        .requestFields(ACCOUNT_CHANGE_PASSWORD_REQUEST_FIELDS)
                        .responseFields(ERROR_RESPONSE_FIELDS)
                        .build())));
    }


}