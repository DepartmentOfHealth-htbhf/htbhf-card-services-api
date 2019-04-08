package uk.gov.dhsc.htbhf.card.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@ApiModel(description = "Cleared and uncleared balances of a card")
public class CardBalance {

    @JsonProperty("availableBalance")
    @ApiModelProperty(name = "availableBalance", notes = "Card balance including transactions that have not yet cleared")
    private BigDecimal availableBalance;

    @JsonProperty("ledgerBalance")
    @ApiModelProperty(name = "ledgerBalance", notes = "Card balance only including transactions that have cleared")
    private BigDecimal ledgerBalance;
}
