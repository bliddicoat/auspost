package au.com.auspost.service.search;

import au.com.auspost.domain.Postage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Component
public class SearchChain {

    @Autowired
    private List<Search> searches;

    public SearchChain() {
    }

    @PostConstruct
    public void init() {
        Collections.sort(searches, AnnotationAwareOrderComparator.INSTANCE);
        Search previous = null;
        for (Search search : searches) {
            if (previous != null) {
                previous.setNextSearch(search);
            }
            previous = search;
        }
    }

    public List<Postage> find(String query) {
        Search search = searches.get(0);
        return search.find(query);
    }
}
