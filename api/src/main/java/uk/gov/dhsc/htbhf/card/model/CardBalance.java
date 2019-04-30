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
@ApiModel(description = "Cleared and uncleared balances of a card")
public class CardBalance {

    @JsonProperty("availableBalanceInPence")
    @ApiModelProperty(name = "availableBalanceInPence", notes = "Card balance (in pence) including transactions that have not yet cleared")
    private Integer availableBalanceInPence;

    @JsonProperty("ledgerBalanceInPence")
    @ApiModelProperty(name = "ledgerBalance", notes = "Card balance (in pence) only including transactions that have cleared")
    private Integer ledgerBalanceInPence;
}
