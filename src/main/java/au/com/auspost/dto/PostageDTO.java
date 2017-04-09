package au.com.auspost.dto;

import au.com.auspost.domain.Postage;
import au.com.auspost.validation.ValidPostcode;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@ApiModel(value = "Postage", description = "Postage details")
public class PostageDTO {
    @ApiModelProperty(value = "The unique identifier of the given postage", readOnly = true)
    private Long id;
    @ApiModelProperty(value = "Postcode for the postage", required = true)
    @ValidPostcode(message="invalid.postcode")
    private String postcode;
    @ApiModelProperty(value = "Suburb for the postage")
    @NotNull(message = "notnull.postage.suburb")
    @Size(max = 100, message = "size.postage.suburb")
    private String suburb;

    public PostageDTO() {
    }

    public PostageDTO(Long id, String postcode, String suburb) {
        this.id = id;
        this.postcode = postcode;
        this.suburb = suburb;
    }

    public PostageDTO(Postage postage) {
        this.id = postage.getId();
        this.postcode = postage.getPostcode();
        this.suburb = postage.getSuburb();
    }

    public Long getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Id", id)
                .add("Postcode", postcode)
                .add("Suburb", suburb)
                .toString();
    }
}