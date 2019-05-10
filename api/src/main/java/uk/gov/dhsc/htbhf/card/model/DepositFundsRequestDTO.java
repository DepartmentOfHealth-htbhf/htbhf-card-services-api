package uk.gov.dhsc.htbhf.card.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@ApiModel(description = "Card deposit funds request object")
public class DepositFundsRequestDTO {

    @NotNull
    @JsonProperty("amountInPence")
    @ApiModelProperty(name = "amountInPence", example = "1240")
    private Integer amountInPence;

    @NotNull
    @JsonProperty("reference")
    @ApiModelProperty(name = "reference", example = "f8b96afa-7fd1-4200-9c1a-407531036b29")
    private String reference;
}
