package ca.arshjudge.finance.gateway;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
public class GatewayFallbackController {
 @GetMapping("/fallback")
 public ResponseEntity<Map<String,String>> fallback(){
   return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("error","Finance service temporarily unavailable","message","Retry later"));
 }
}
