package ca.arshjudge.finance.repository;
import ca.arshjudge.finance.domain.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByOwnerSubject(String ownerSubject);
    Optional<Account> findByIdAndOwnerSubject(Long id, String ownerSubject);
    @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select a from Account a where a.id in :ids order by a.id")
    List<Account> lockAccounts(@Param("ids") Collection<Long> ids);
}
