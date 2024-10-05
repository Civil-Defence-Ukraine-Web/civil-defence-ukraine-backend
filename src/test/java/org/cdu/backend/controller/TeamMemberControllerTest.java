package org.cdu.backend.controller;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.cdu.backend.dto.team.member.TeamMemberCreateRequestDto;
import org.cdu.backend.dto.team.member.TeamMemberResponseDto;
import org.cdu.backend.dto.team.member.TeamMemberUpdateRequestDto;
import org.cdu.backend.model.TeamMember;
import org.cdu.backend.service.impl.DropboxImageServiceImpl;
import org.cdu.backend.util.TeamMemberUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamMemberControllerTest {
    protected static MockMvc mockMvc;

    private static final String BASIC_URL_ENDPOINT = "/team";

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DropboxImageServiceImpl imageService;

    @BeforeAll
    public static void setUp(@Autowired WebApplicationContext webApplicationContext,
                             @Autowired DataSource dataSource) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        teardown(dataSource);
    }

    @AfterEach
    public void afterEach(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @SneakyThrows
    @BeforeEach
    public void beforeEach(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource(
                            "database/team/member/add-three-team-members-to-database.sql")
            );
        }
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource(
                            "database/team/member/delete-team-members-from-database.sql")
            );
        }
    }

    @DisplayName("""
            Verify findAll endpoint works correctly and returns all members
            """)
    @Test
    void findAll_GivenTeamMembers_ShouldReturnAllTeamMembers() throws Exception {
        List<TeamMemberResponseDto> expected = TeamMemberUtil.createTeamMemberResponseDtoList();

        MvcResult result = mockMvc.perform(
                        get(BASIC_URL_ENDPOINT).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TeamMemberResponseDto[] actual = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(),
                        TeamMemberResponseDto[].class);
        assertEquals(expected.size(), actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @DisplayName("""
            Verify findById endpoint works correctly and returns correct team member
            """)
    @Test
    void findById_WithValidId_ShouldReturnCorrectTeamMember() throws Exception {
        TeamMemberResponseDto expected = TeamMemberUtil.createFirstMemberResponseDto();

        MvcResult result = mockMvc.perform(
                        get(BASIC_URL_ENDPOINT + "/" + expected.id())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        TeamMemberResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(),
                        TeamMemberResponseDto.class);
        assertEquals(expected, actual);
    }

    @DisplayName("""
            Verify save endpoint works correctly, saves and returns correct team member
            """)
    @Test
    void save_ValidCreateDto_ShouldReturnCorrectTeamMemberDto() throws Exception {
        TeamMemberCreateRequestDto requestDto = TeamMemberUtil.createFirstMemberCreateRequestDto();
        TeamMemberResponseDto expected = TeamMemberUtil.createFirstMemberResponseDto();

        MockMultipartFile imageFile = new MockMultipartFile("image",
                "team_image_1.jpg", MediaType.IMAGE_JPEG_VALUE,
                "test image".getBytes());

        Mockito.when(imageService.save(imageFile, DropboxImageServiceImpl.ImageType.NEWS_IMAGE))
                .thenReturn(imageFile.getOriginalFilename());

        MockMultipartFile jsonPart = new MockMultipartFile(
                "requestDto",
                "requestDto",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(requestDto));

        MvcResult result = mockMvc.perform(
                        multipart(HttpMethod.POST, BASIC_URL_ENDPOINT)
                                .file(imageFile)
                                .file(jsonPart)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                ).andExpect(status().isOk())
                .andReturn();

        TeamMemberResponseDto actual =
                objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                        TeamMemberResponseDto.class);
        reflectionEquals(expected, actual, "id");
    }

    @DisplayName("""
            Verify update endpoint works correctly, updates and returns correct team member
            """)
    @Test
    void update_WithValidIdAndUpdateDto_ShouldReturnCorrectTeamMemberDto() throws Exception {
        TeamMemberResponseDto expected = TeamMemberUtil.createFirstMemberResponseDto();
        TeamMemberUpdateRequestDto requestDto = TeamMemberUtil.createFirstMemberUpdateRequestDto();

        MockMultipartFile imageFile = new MockMultipartFile("image",
                "team_image_1.jpg", MediaType.IMAGE_JPEG_VALUE,
                "test image".getBytes());

        Mockito.when(imageService.save(imageFile, DropboxImageServiceImpl.ImageType.NEWS_IMAGE))
                .thenReturn(imageFile.getOriginalFilename());

        MockMultipartFile jsonPart = new MockMultipartFile(
                "requestDto",
                "requestDto",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(requestDto));

        MvcResult result = mockMvc.perform(
                        multipart(HttpMethod.PUT, BASIC_URL_ENDPOINT + "/2")
                                .file(imageFile)
                                .file(jsonPart)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                ).andExpect(status().isOk())
                .andReturn();

        TeamMemberResponseDto actual =
                objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                        TeamMemberResponseDto.class);
        reflectionEquals(expected, actual, "id");
    }

    @DisplayName("""
            Verify delete endpoint works correctly and returns success code
            """)
    @Test
    void delete_WithValidId_ShouldReturnSuccess() throws Exception {
        TeamMember memberToDelete = TeamMemberUtil.createFirstTeamMember();
        mockMvc.perform(delete(BASIC_URL_ENDPOINT + "/" + memberToDelete.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
    }
}
