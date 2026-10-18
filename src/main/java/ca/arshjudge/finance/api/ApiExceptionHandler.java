package ca.arshjudge.finance.api;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(NoSuchElementException.class)
 ResponseEntity<Map<String,Object>> notFound(NoSuchElementException ex){return error(HttpStatus.NOT_FOUND,ex.getMessage());}
 @ExceptionHandler(IllegalArgumentException.class)
 ResponseEntity<Map<String,Object>> badRequest(IllegalArgumentException ex){return error(HttpStatus.BAD_REQUEST,ex.getMessage());}
 @ExceptionHandler(IllegalStateException.class)
 ResponseEntity<Map<String,Object>> conflict(IllegalStateException ex){return error(HttpStatus.CONFLICT,ex.getMessage());}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException ex){
   Map<String,String> fields=new LinkedHashMap<>();
   ex.getBindingResult().getFieldErrors().forEach(e->fields.putIfAbsent(e.getField(),e.getDefaultMessage()));
   Map<String,Object> body=new LinkedHashMap<>(); body.put("timestamp",Instant.now());body.put("status",400);body.put("error","Validation failed");body.put("fields",fields);
   return ResponseEntity.badRequest().body(body);
 }
 private ResponseEntity<Map<String,Object>> error(HttpStatus status,String message){
   Map<String,Object> body=new LinkedHashMap<>();body.put("timestamp",Instant.now());body.put("status",status.value());body.put("error",status.getReasonPhrase());body.put("message",message);
   return ResponseEntity.status(status).body(body);
 }
}
