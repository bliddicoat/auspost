package au.com.auspost.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PostageRepository extends CrudRepository<Postage, Long> {

    @Query("SELECT p FROM Postage p WHERE p.postcode LIKE :postcode% ORDER BY p.postcode")
    List<Postage> findAllByPostcode(@Param("postcode") String postcode);

    @Query("SELECT p FROM Postage p WHERE lower(p.suburb) LIKE concat(lower(:suburb), '%') ORDER BY p.suburb")
    List<Postage> findAllBySuburb(@Param("suburb") String suburb);

    @Query("SELECT p FROM Postage p WHERE p.postcode = :postcode AND lower(p.suburb) = lower(:suburb)")
    Postage findByPostcodeAndSuburb(@Param("postcode") String postcode, @Param("suburb") String suburb);
}
