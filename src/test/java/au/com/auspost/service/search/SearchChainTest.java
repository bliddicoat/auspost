package au.com.auspost.service.search;

import au.com.auspost.domain.Postage;
import au.com.auspost.domain.PostageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SearchChainTest {
    @Mock
    private PostageRepository postageRepository;
    @InjectMocks
    private PostcodeSearch postcodeSearch;
    @InjectMocks
    private SuburbSearch suburbSearch;
    private NotFoundSearch notFoundSearch;
    private static final String POST_CODE = "3000";
    private static final String SUBURB = "Melbourne";
    private static final String INVALID_QUERY = "";

    @Before
    public void setUp() {
        this.notFoundSearch = new NotFoundSearch();
        this.suburbSearch = new SuburbSearch(notFoundSearch);
        this.postcodeSearch = new PostcodeSearch(suburbSearch);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void searchByPostcode() {
        List<Postage> postages = Arrays.asList(new Postage(POST_CODE, SUBURB));
        when(postageRepository.findAllByPostcode(POST_CODE)).thenReturn(postages);
        List<Postage> result = postcodeSearch.find(POST_CODE);
        assertEquals(POST_CODE, result.get(0).getPostcode());
        verify(postageRepository, times(1)).findAllByPostcode(POST_CODE);
    }

    @Test
    public void searchBySuburb() {
        List<Postage> postages = Arrays.asList(new Postage(POST_CODE, SUBURB));
        when(postageRepository.findAllBySuburb(SUBURB)).thenReturn(postages);
        List<Postage> result = postcodeSearch.find(SUBURB);
        assertEquals(SUBURB, result.get(0).getSuburb());
        verify(postageRepository, times(1)).findAllBySuburb(SUBURB);
    }

    @Test
    public void searchByInvalidQuery() {
        List<Postage> result = postcodeSearch.find(INVALID_QUERY);
        assertEquals(0, result.size());
        verify(postageRepository, times(0)).findAllByPostcode(POST_CODE);
        verify(postageRepository, times(0)).findAllBySuburb(SUBURB);
    }
}
