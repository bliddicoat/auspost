package au.com.auspost.service.search;

import au.com.auspost.domain.Postage;
import au.com.auspost.domain.PostageRepository;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PostcodeSearch implements Search {

    private static final Integer POSTCODE_SIZE = 4;

    @Autowired
    private PostageRepository postageRepository;
    private Search nextSearch;

    public PostcodeSearch() {
    }

    public PostcodeSearch(Search nextSearch) {
        this.nextSearch = nextSearch;
    }

    @Override
    public List<Postage> find(String query) {
        if (isPostcodeSearch(query)) {
            return postageRepository.findAllByPostcode(query);
        }
        return nextSearch.find(query);
    }

    private Boolean isPostcodeSearch(String query) {
        return !Strings.isNullOrEmpty(query) && query.length() <= POSTCODE_SIZE && Ints.tryParse(query) != null;
    }

    @Override
    public void setNextSearch(Search nextSearch) {
        this.nextSearch = nextSearch;
    }
}
