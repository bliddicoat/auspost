package au.com.auspost.service.search;

import au.com.auspost.domain.Postage;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class NotFoundSearch implements Search {

    public NotFoundSearch() {
    }

    @Override
    public List<Postage> find(String query) {
        return new ArrayList<>();
    }

    @Override
    public void setNextSearch(Search nextSearch) {
    }
}
