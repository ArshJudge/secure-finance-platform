package ca.arshjudge.finance.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="accounts", indexes=@Index(name="idx_accounts_owner", columnList="owner_subject"))
public class Account {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=200) private String ownerSubject;
    @Column(nullable=false, unique=true, length=32) private String accountNumber;
    @Column(nullable=false, length=3) private String currency;
    @Column(nullable=false, precision=19, scale=2) private BigDecimal balance;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private AccountStatus status;
    @Column(nullable=false, updatable=false) private Instant createdAt;
    @Version private Long version;
    protected Account() {}
    public Account(String ownerSubject, String accountNumber, String currency, BigDecimal openingBalance) {
        this.ownerSubject=ownerSubject; this.accountNumber=accountNumber; this.currency=currency;
        this.balance=openingBalance; this.status=AccountStatus.ACTIVE;
    }
    @PrePersist void created() { createdAt=Instant.now(); }
    public Long getId(){return id;} public String getOwnerSubject(){return ownerSubject;}
    public String getAccountNumber(){return accountNumber;} public String getCurrency(){return currency;}
    public BigDecimal getBalance(){return balance;} public AccountStatus getStatus(){return status;}
    public Instant getCreatedAt(){return createdAt;}
    public void debit(BigDecimal amount) {
        if(status!=AccountStatus.ACTIVE) throw new IllegalStateException("Source account is not active");
        if(balance.compareTo(amount)<0) throw new IllegalArgumentException("Insufficient funds");
        balance=balance.subtract(amount);
    }
    public void credit(BigDecimal amount) {
        if(status!=AccountStatus.ACTIVE) throw new IllegalStateException("Destination account is not active");
        balance=balance.add(amount);
    }
}
