package com.tamlog.blog.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class BaseControllerTest {
    protected static final String DEFAULT_RESTDOCS_PATH = "{class_name}/{method_name}/";
//    protected int port = 8080;
//    protected RequestSpecification spec;
//
//    @BeforeEach
//    void setUp() throws JsonProcessingException, JSONException {
//        RestAssured.port = port;
//
//        for (int i = 0; i < 10; i++) {
//        }
//    }
//
//    @BeforeEach
//    void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
//        this.spec = new RequestSpecBuilder()
//                .setPort(port)
//                .addFilter(documentationConfiguration(restDocumentation)
//                        .operationPreprocessors()
//                        .withRequestDefaults(prettyPrint())
//                        .withResponseDefaults(prettyPrint()))
//                .build();
//    }

    @Autowired
    protected MockMvc mockMvc;
}
