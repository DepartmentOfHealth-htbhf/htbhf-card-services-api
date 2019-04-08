package uk.gov.dhsc.htbhf.card.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Data
@Builder
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@ApiModel(description = "The card object")
public class CardDTO {

    @NotNull
    @JsonProperty("firstName")
    @ApiModelProperty(notes = "First name of the card owner", example = "Homer")
    private String firstName;

    @NotNull
    @JsonProperty("lastName")
    @ApiModelProperty(notes = "Last name of the card owner", example = "Simpson")
    private String lastName;

    @NotNull
    @Valid
    @JsonProperty("address")
    @ApiModelProperty(notes = "The address the card is registered to")
    private AddressDTO address;

    @NotNull
    @Past
    @JsonProperty("dateOfBirth")
    @ApiModelProperty(notes = "The date of birth, in the format YYYY-MM-DD", example = "1985-12-30")
    private final LocalDate dateOfBirth;

    @JsonProperty("email")
    @ApiModelProperty(notes = "Email address of the card owner", example = "homer@simpson.com")
    private String email;

    @JsonProperty("mobile")
    @ApiModelProperty(notes = "Mobile number of the card owner", example = "07700900000")
    private String mobile;

    @NotNull
    @JsonProperty("claimId")
    @ApiModelProperty(notes = "Unique id identifying new card request", example = "e2bb5fcb-da0f-4f2c-8795-853e99a91209")
    private String claimId;
}
