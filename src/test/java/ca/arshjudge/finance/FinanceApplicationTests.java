package ca.arshjudge.finance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(properties={
 "JWT_ISSUER_URI=https://issuer.example.test",
 "JWT_AUDIENCE=finance-api",
 "spring.datasource.url=jdbc:h2:mem:finance;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
 "spring.flyway.enabled=false",
 "spring.jpa.hibernate.ddl-auto=create-drop"
})
class FinanceApplicationTests {
 @Test void contextLoads() {}
}
