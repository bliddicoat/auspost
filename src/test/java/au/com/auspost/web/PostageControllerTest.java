package au.com.auspost.web;

import au.com.auspost.dto.PostageDTO;
import au.com.auspost.service.PostageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PostageController.class)
@AutoConfigureMockMvc(secure=false)
public class PostageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostageService postageService;
    private static final Long ID = 1L;
    private static final String POST_CODE = "3000";
    private static final String SUBURB = "Melbourne";
    private static final String API_POSTCODES_URL = "/api/postcodes/";
    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    private PostageDTO postageDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postageDTO = new PostageDTO(ID, POST_CODE, SUBURB);
    }

    @Test
    public void testSearchSuccess() throws Exception {
        when(postageService.search(any())).thenReturn(new ArrayList<>(Arrays.asList(postageDTO)));
        mockMvc.perform(get(API_POSTCODES_URL + "search/" + POST_CODE).accept(MediaType.parseMediaType(APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].postcode").value(postageDTO.getPostcode()))
                .andExpect(jsonPath("$.[*].suburb").value(postageDTO.getSuburb()));
    }

    @Test
    public void testCreateSuccess() throws Exception {
        when(postageService.isPostageExist(any())).thenReturn(false);
        when(postageService.create(any())).thenReturn(postageDTO);
        mockMvc.perform(post(API_POSTCODES_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postageDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/postcodes/" + postageDTO.getId())));
    }

    @Test
    public void testCreateConflict() throws Exception {
        when(postageService.isPostageExist(any())).thenReturn(true);
        mockMvc.perform(post(API_POSTCODES_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postageDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testPostageByIdSuccess() throws Exception {
        when(postageService.findById(any())).thenReturn(postageDTO);
        mockMvc.perform(get(API_POSTCODES_URL + postageDTO.getId()).accept(MediaType.parseMediaType(APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    public void testPostageByIdNotFound() throws Exception {
        when(postageService.findById(any())).thenReturn(null);
        mockMvc.perform(get(API_POSTCODES_URL + postageDTO.getId()).accept(MediaType.parseMediaType(APPLICATION_JSON)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
