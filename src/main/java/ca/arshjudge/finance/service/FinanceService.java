package ca.arshjudge.finance.service;

import ca.arshjudge.finance.api.dto.*;
import ca.arshjudge.finance.domain.*;
import ca.arshjudge.finance.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
public class FinanceService {
    private final AccountRepository accounts;
    private final TransferRepository transfers;
    public FinanceService(AccountRepository accounts, TransferRepository transfers){this.accounts=accounts;this.transfers=transfers;}

    @Transactional
    public AccountResponse createAccount(String subject, CreateAccountRequest request) {
        String number = "PF" + UUID.randomUUID().toString().replace("-", "").substring(0, 18).toUpperCase();
        Account a = accounts.save(new Account(subject, number, request.currency(), request.openingBalance()));
        return AccountResponse.from(a);
    }
    @Transactional(readOnly=true)
    public List<AccountResponse> listAccounts(String subject) {
        return accounts.findByOwnerSubject(subject).stream().map(AccountResponse::from).toList();
    }
    @Transactional(readOnly=true)
    public AccountResponse getAccount(String subject, Long id) {
        return AccountResponse.from(accounts.findByIdAndOwnerSubject(id,subject).orElseThrow(() -> new NoSuchElementException("Account not found")));
    }
    @Transactional
    public TransferResponse transfer(String subject, TransferRequest request) {
        if(request.fromAccountId().equals(request.toAccountId())) throw new IllegalArgumentException("Source and destination accounts must differ");
        List<Account> locked = accounts.lockAccounts(List.of(request.fromAccountId(), request.toAccountId()));
        if(locked.size()!=2) throw new NoSuchElementException("Source or destination account not found");
        Map<Long,Account> byId = new HashMap<>(); locked.forEach(a -> byId.put(a.getId(),a));
        Account from=byId.get(request.fromAccountId()), to=byId.get(request.toAccountId());
        if(!from.getOwnerSubject().equals(subject)) throw new NoSuchElementException("Source account not found");
        if(!from.getCurrency().equals(request.currency()) || !to.getCurrency().equals(request.currency()))
            throw new IllegalArgumentException("Transfer currency must match both account currencies");
        from.debit(request.amount());
        to.credit(request.amount());
        Transfer transfer=transfers.save(new Transfer(UUID.randomUUID().toString(),from,to,request.amount(),request.currency()));
        return TransferResponse.from(transfer);
    }
    @Transactional(readOnly=true)
    public List<TransferResponse> history(String subject, Long accountId) {
        Account account=accounts.findByIdAndOwnerSubject(accountId,subject).orElseThrow(() -> new NoSuchElementException("Account not found"));
        return transfers.findTop50ByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(account.getId(),account.getId())
            .stream().map(TransferResponse::from).toList();
    }
}
