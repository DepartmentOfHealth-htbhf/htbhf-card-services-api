package uk.gov.dhsc.htbhf.card.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@ApiModel(description = "Card fund transfer request object")
public class TransferRequestDTO {

    @NotNull
    @JsonProperty("amount")
    @ApiModelProperty(name = "amount", example = "12.40")
    private BigDecimal amount;

    @NotNull
    @JsonProperty("reference")
    @ApiModelProperty(name = "reference", example = "f8b96afa-7fd1-4200-9c1a-407531036b29")
    private String reference;
}
