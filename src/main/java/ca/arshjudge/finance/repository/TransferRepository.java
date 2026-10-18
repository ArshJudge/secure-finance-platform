package ca.arshjudge.finance.repository;
import ca.arshjudge.finance.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TransferRepository extends JpaRepository<Transfer,Long> {
    List<Transfer> findTop50ByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(Long fromId, Long toId);
}
