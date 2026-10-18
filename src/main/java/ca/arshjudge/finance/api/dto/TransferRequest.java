package ca.arshjudge.finance.api.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record TransferRequest(@NotNull @Positive Long fromAccountId, @NotNull @Positive Long toAccountId,
 @NotNull @DecimalMin(value="0.01") @Digits(integer=17,fraction=2) BigDecimal amount,
 @NotBlank @Pattern(regexp="[A-Z]{3}") String currency) {}
