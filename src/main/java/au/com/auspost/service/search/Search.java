package au.com.auspost.service.search;

import au.com.auspost.domain.Postage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Search {
    List<Postage> find(String query);
    void setNextSearch(Search nextSearch);
}
