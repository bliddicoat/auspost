package au.com.auspost.service;

import au.com.auspost.domain.Postage;
import au.com.auspost.domain.PostageRepository;
import au.com.auspost.dto.PostageDTO;
import au.com.auspost.service.search.SearchChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostageService {

    @Autowired
    private PostageRepository postageRepository;

    @Autowired
    private SearchChain searchChain;

    public List<PostageDTO> search(String query) {
        return searchChain.find(query).stream()
                .map(entity -> new PostageDTO(entity.getId(), entity.getPostcode(), entity.getSuburb()))
                .collect(Collectors.toList());
    }

    @Transactional
    public PostageDTO create(PostageDTO postageDTO) {
        Postage newPostage = new Postage();
        newPostage.setPostcode(postageDTO.getPostcode());
        newPostage.setSuburb(postageDTO.getSuburb());
        Postage savedPostage = postageRepository.save(newPostage);
        return new PostageDTO(savedPostage.getId(), savedPostage.getPostcode(), savedPostage.getSuburb());
    }

    public Boolean isPostageExist(PostageDTO postageDTO) {
        return postageRepository.findByPostcodeAndSuburb(postageDTO.getPostcode(), postageDTO.getSuburb()) != null;
    }

    public PostageDTO findById(Long id) {
        PostageDTO postageDTO = null;
        Postage postage = postageRepository.findOne(id);
        if (postage != null) {
            postageDTO = new PostageDTO(postage);
        }
        return postageDTO;
    }
}
