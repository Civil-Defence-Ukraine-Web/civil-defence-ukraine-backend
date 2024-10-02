package org.cdu.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamMemberControllerTest {
    protected static MockMvc mockMvc;

    private static final String BASIC_URL_ENDPOINT = "/team";

    @Autowired
    private ObjectMapper objectMapper;


}
