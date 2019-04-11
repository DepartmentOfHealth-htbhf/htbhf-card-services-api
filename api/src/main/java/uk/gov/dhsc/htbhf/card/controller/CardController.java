package uk.gov.dhsc.htbhf.card.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dhsc.htbhf.card.model.CardBalance;
import uk.gov.dhsc.htbhf.card.model.CardDTO;
import uk.gov.dhsc.htbhf.card.model.CardResponse;
import uk.gov.dhsc.htbhf.card.model.TransferRequestDTO;

import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.Valid;

@RestController
@RequestMapping("v1/cards")
@AllArgsConstructor
@Slf4j
@Api
public class CardController {

    @PostMapping
    @ApiOperation("Create a new card")
    @ApiResponses({@ApiResponse(code = 201, message = "The response to the create card request")})
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponse createCard(@RequestBody @Valid @ApiParam("Details of the new card") CardDTO card) {
        log.debug("Received new card request");
        return CardResponse.builder()
                .cardAccountId(UUID.randomUUID().toString())
                .build();
    }

    @PostMapping("/{cardId}/transfer")
    @ApiOperation("Transfer funds to a card")
    @ApiResponses({@ApiResponse(code = 200, message = "Payment reference")})
    public void transferFunds(@PathVariable("cardId") String cardId,
                              @RequestBody @Valid @ApiParam("Balance transfer request information") TransferRequestDTO transferRequest) {
        log.debug("Received new funds transfer request");
    }

    @GetMapping("/{cardId}/balance")
    @ApiOperation("Transfer funds to a card")
    @ApiResponses({@ApiResponse(code = 200, message = "Card balance", response = CardBalance.class)})
    public CardBalance getBalance(@PathVariable("cardId") String cardId) {
        log.debug("Received request for card balance");
        return CardBalance.builder()
                .availableBalance(BigDecimal.TEN)
                .ledgerBalance(BigDecimal.ONE)
                .build();
    }


}
