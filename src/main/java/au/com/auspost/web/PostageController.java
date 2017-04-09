package au.com.auspost.web;

import au.com.auspost.dto.MessageDTO;
import au.com.auspost.dto.PostageDTO;
import au.com.auspost.service.PostageService;
import au.com.auspost.web.exception.NotFoundException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/postcodes")
@Api("/api/postcodes")
public class PostageController {

    @Autowired
    private PostageService postageService;

    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find all postages", notes = "Retrieves a collection of postages by postcode or suburb", response = PostageDTO[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PostageDTO[].class)
    })
    public @ResponseBody List<PostageDTO> search(@PathVariable("query") String query) {
        return postageService.search(query);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create postage", notes = "Creates a new postage", response = PostageDTO.class, authorizations = {@Authorization(value="basicAuth")})
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request", response = MessageDTO.class),
            @ApiResponse(code = 409, message = "Unable to create as postage already exists")
    })
    public ResponseEntity<Void> create(@Valid @RequestBody PostageDTO postageDTO, UriComponentsBuilder ucBuilder) {
        if (postageService.isPostageExist(postageDTO)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        PostageDTO newPostageDTO = postageService.create(postageDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/postcodes/{id}").buildAndExpand(newPostageDTO.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Find postage by id", notes = "Retrieves a postage by id", response = PostageDTO[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = PostageDTO.class),
            @ApiResponse(code = 404, message = "Not found", response = MessageDTO.class)
    })
    public @ResponseBody PostageDTO postageById(@PathVariable Long id) {
        PostageDTO postageDTO = postageService.findById(id);
        if (postageDTO == null) {
            throw new NotFoundException(id);
        }
        return postageDTO;
    }
}