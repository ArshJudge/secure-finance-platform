package ca.arshjudge.finance.api;

import ca.arshjudge.finance.api.dto.*;
import ca.arshjudge.finance.service.FinanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FinanceController {
    private final FinanceService service;
    public FinanceController(FinanceService service){this.service=service;}
    @PostMapping("/accounts") @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody CreateAccountRequest request) {
        return service.createAccount(jwt.getSubject(),request);
    }
    @GetMapping("/accounts")
    public List<AccountResponse> list(@AuthenticationPrincipal Jwt jwt){return service.listAccounts(jwt.getSubject());}
    @GetMapping("/accounts/{id}")
    public AccountResponse get(@AuthenticationPrincipal Jwt jwt,@PathVariable Long id){return service.getAccount(jwt.getSubject(),id);}
    @PostMapping("/transfers") @ResponseStatus(HttpStatus.CREATED)
    public TransferResponse transfer(@AuthenticationPrincipal Jwt jwt,@Valid @RequestBody TransferRequest request) {
        return service.transfer(jwt.getSubject(),request);
    }
    @GetMapping("/accounts/{id}/transactions")
    public List<TransferResponse> history(@AuthenticationPrincipal Jwt jwt,@PathVariable Long id){return service.history(jwt.getSubject(),id);}
}
