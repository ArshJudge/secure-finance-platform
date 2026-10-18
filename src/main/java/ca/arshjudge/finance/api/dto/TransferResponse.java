package ca.arshjudge.finance.api.dto;
import ca.arshjudge.finance.domain.Transfer;
import java.math.BigDecimal;
import java.time.Instant;
public record TransferResponse(String reference,Long fromAccountId,Long toAccountId,BigDecimal amount,String currency,String status,Instant createdAt) {
 public static TransferResponse from(Transfer t){return new TransferResponse(t.getReference(),t.getFromAccountId(),t.getToAccountId(),t.getAmount(),t.getCurrency(),t.getStatus(),t.getCreatedAt());}
}
