package au.com.auspost.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Message", description = "A message containing more information why the operation failed")
public class MessageDTO {

    @ApiModelProperty(value = "The message detail", readOnly = true)
    private String message;

    public MessageDTO(String message) {
        this.message = message;
    }

    public MessageDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
