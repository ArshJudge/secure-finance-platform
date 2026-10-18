package ca.arshjudge.finance.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="transfers")
public class Transfer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=36) private String reference;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="from_account_id", nullable=false) private Account fromAccount;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="to_account_id", nullable=false) private Account toAccount;
    @Column(nullable=false, precision=19, scale=2) private BigDecimal amount;
    @Column(nullable=false, length=3) private String currency;
    @Column(nullable=false, length=20) private String status;
    @Column(nullable=false, updatable=false) private Instant createdAt;
    protected Transfer() {}
    public Transfer(String reference, Account from, Account to, BigDecimal amount, String currency) {
        this.reference=reference; this.fromAccount=from; this.toAccount=to; this.amount=amount; this.currency=currency; this.status="COMPLETED";
    }
    @PrePersist void created(){createdAt=Instant.now();}
    public Long getId(){return id;} public String getReference(){return reference;}
    public Long getFromAccountId(){return fromAccount.getId();} public Long getToAccountId(){return toAccount.getId();}
    public BigDecimal getAmount(){return amount;} public String getCurrency(){return currency;}
    public String getStatus(){return status;} public Instant getCreatedAt(){return createdAt;}
}
