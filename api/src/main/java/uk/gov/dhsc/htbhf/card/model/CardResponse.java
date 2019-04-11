package uk.gov.dhsc.htbhf.card.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@ApiModel(description = "The response for making creating a new card")
public class CardResponse {

    @JsonProperty("cardAccountId")
    @ApiModelProperty(notes = "The id of the account for the new card", example = "19d39727-dcc4-487a-8ff0-e86996540617")
    private String cardAccountId;
}
