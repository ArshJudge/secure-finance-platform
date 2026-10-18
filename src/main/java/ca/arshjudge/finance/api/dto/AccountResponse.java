package ca.arshjudge.finance.api.dto;
import ca.arshjudge.finance.domain.Account;
import java.math.BigDecimal;
import java.time.Instant;
public record AccountResponse(Long id,String accountNumber,String currency,BigDecimal balance,String status,Instant createdAt) {
 public static AccountResponse from(Account a){return new AccountResponse(a.getId(),a.getAccountNumber(),a.getCurrency(),a.getBalance(),a.getStatus().name(),a.getCreatedAt());}
}
