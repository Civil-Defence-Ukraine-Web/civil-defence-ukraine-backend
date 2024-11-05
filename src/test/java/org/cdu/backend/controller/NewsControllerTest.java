package org.cdu.backend.controller;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.cdu.backend.dto.news.NewsCreateRequestDto;
import org.cdu.backend.dto.news.NewsResponseDto;
import org.cdu.backend.dto.news.NewsUpdateRequestDto;
import org.cdu.backend.service.impl.DropboxImageServiceImpl;
import org.cdu.backend.util.NewsUtil;
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
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsControllerTest {
    protected static MockMvc mockMvc;

    private static final String BASIC_PUBLIC_URL = "/public";
    private static final String BASIC_ADMIN_URL = "/admin";
    private static final String BASIC_URL_ENDPOINT = "/news";
    private static final String SEARCH_PARAM_TITLE = "title";
    private static final String SEARCH_PARAM_TYPE = "type";
    private static final String SEARCH_PARAM_LINK = "link";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DataSource dataSource;

    @MockBean
    private DropboxImageServiceImpl imageService;

    @SneakyThrows
    private boolean isPostgres(DataSource dataSource) {
        return JdbcUtils.extractDatabaseMetaData(dataSource,
                        DatabaseMetaData::getDatabaseProductName)
                .toLowerCase().contains("postgresql");
    }

    @BeforeAll
    public static void setUp(@Autowired WebApplicationContext webApplicationContext,
                             @Autowired DataSource dataSource) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        teardown(dataSource);
    }

    @SneakyThrows
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
                    new ClassPathResource("database/news/add-three-news-to-database.sql")
            );
        }
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/news/delete-news-from-database.sql")
            );
        }
    }

    @DisplayName("""
            Verify findAll endpoint works correctly and returns correct DTO list
            """)
    @Test
    void findAll_GivenNews_ShouldReturnAllNews() throws Exception {
        List<NewsResponseDto> expected = NewsUtil.createThreeNewsDtoList();

        MvcResult result = mockMvc.perform(
                        get(BASIC_PUBLIC_URL + BASIC_URL_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        NewsResponseDto[] actual = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(), NewsResponseDto[].class);
        assertEquals(expected.size(), actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @DisplayName("""
            Verify findById endpoint works correctly end returns correct DTO
            """)
    @Test
    void findById_WithValidId_ShouldReturnCorrectNews() throws Exception {
        NewsResponseDto expected = NewsUtil.createFirstNewsResponseDto();

        MvcResult result = mockMvc.perform(
                        get(BASIC_PUBLIC_URL + BASIC_URL_ENDPOINT + "/" + expected.id())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        NewsResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(), NewsResponseDto.class);
        assertEquals(expected, actual);
    }

    @DisplayName("""
            Verify search endpoint works correctly and returns correct DTO list with params
            """)
    @Test
    void search_WithValidSearchParams_ShouldReturnFirstNews() throws Exception {
        List<NewsResponseDto> expected = List.of(NewsUtil.createFirstNewsResponseDto());

        MvcResult result = mockMvc.perform(
                        get(BASIC_PUBLIC_URL + BASIC_URL_ENDPOINT + "/search")
                                .param(SEARCH_PARAM_TITLE, expected.getFirst().title())
                                .param(SEARCH_PARAM_TYPE, expected.getFirst().type().toString())
                                .param(SEARCH_PARAM_LINK, expected.getFirst().link())
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        NewsResponseDto[] actual = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(), NewsResponseDto[].class);
        assertEquals(expected.size(), actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @DisplayName("""
            Verify save endpoint works correctly and returns correct DTO
            """)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void save_ValidCreateDto_ShouldReturnCorrectNewsDto() throws Exception {
        teardown(dataSource); //For postgreSql support
        NewsCreateRequestDto createRequestDto = NewsUtil.createFirstNewsCreateRequestDto();
        NewsResponseDto expected = NewsUtil.createFirstNewsResponseDto();

        MockMultipartFile imageFile = new MockMultipartFile("image",
                "image_1.jpg", MediaType.IMAGE_JPEG_VALUE,
                "test image".getBytes());

        Mockito.when(imageService.save(imageFile, DropboxImageServiceImpl.ImageType.NEWS_IMAGE))
                .thenReturn(imageFile.getOriginalFilename());

        MockMultipartFile jsonPart = new MockMultipartFile(
                "requestDto",
                "requestDto",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(createRequestDto));

        MvcResult result = mockMvc.perform(
                        multipart(HttpMethod.POST, BASIC_ADMIN_URL + BASIC_URL_ENDPOINT)
                                .file(imageFile)
                                .file(jsonPart)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                ).andExpect(status().isOk())
                .andReturn();

        NewsResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(), NewsResponseDto.class);
        reflectionEquals(expected, actual, "id", "publicationDate");
    }

    @DisplayName("""
            Verify update endpoint works correctly, updates and returns correct DTO
            """)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void update_ValidIdAndUpdateDto_ShouldReturnCorrectNewsDto() throws Exception {
        NewsResponseDto expected = NewsUtil.createFirstNewsResponseDto();
        NewsUpdateRequestDto updateToFirstNewsRequestDto =
                NewsUtil.createUpdateToFirstNewsRequestDto();

        MockMultipartFile imageFile = new MockMultipartFile("image",
                "image_1.jpg", MediaType.IMAGE_JPEG_VALUE,
                "updated test image".getBytes());

        Mockito.when(imageService.save(imageFile, DropboxImageServiceImpl.ImageType.NEWS_IMAGE))
                .thenReturn(imageFile.getOriginalFilename());

        MockMultipartFile jsonPart = new MockMultipartFile(
                "requestDto",
                "requestDto",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(updateToFirstNewsRequestDto));

        MvcResult result = mockMvc.perform(
                        multipart(HttpMethod.PUT, BASIC_ADMIN_URL + BASIC_URL_ENDPOINT + "/2")
                                .file(imageFile)
                                .file(jsonPart)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                ).andExpect(status().isOk())
                .andReturn();

        NewsResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsByteArray(), NewsResponseDto.class);
        reflectionEquals(expected, actual, "id");
    }

    @DisplayName("""
            Verify delete endpoint works correctly with success status code
            """)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void delete_ValidId_ShouldReturnSuccess() throws Exception {
        NewsResponseDto newsToDelete = NewsUtil.createFirstNewsResponseDto();
        mockMvc.perform(delete(BASIC_ADMIN_URL + BASIC_URL_ENDPOINT + "/" + newsToDelete.id())
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
    }
}
