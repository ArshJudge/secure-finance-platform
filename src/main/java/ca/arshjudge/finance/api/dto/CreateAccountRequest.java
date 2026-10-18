package ca.arshjudge.finance.api.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record CreateAccountRequest(@NotBlank @Pattern(regexp="[A-Z]{3}") String currency,
 @NotNull @DecimalMin("0.00") @Digits(integer=17,fraction=2) BigDecimal openingBalance) {}
