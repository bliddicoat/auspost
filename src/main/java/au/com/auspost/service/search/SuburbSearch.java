package au.com.auspost.service.search;

import au.com.auspost.domain.Postage;
import au.com.auspost.domain.PostageRepository;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class SuburbSearch implements Search {

    @Autowired
    private PostageRepository postageRepository;
    private Search nextSearch;

    public SuburbSearch() {
    }

    public SuburbSearch(Search nextSearch) {
        this.nextSearch = nextSearch;
    }

    @Override
    public List<Postage> find(String query) {
        if (isSuburbSearch(query)) {
            return postageRepository.findAllBySuburb(query);
        }
        return nextSearch.find(query);
    }

    private Boolean isSuburbSearch(String query) {
        return !Strings.isNullOrEmpty(query);
    }

    @Override
    public void setNextSearch(Search nextSearch) {
        this.nextSearch = nextSearch;
    }
}
