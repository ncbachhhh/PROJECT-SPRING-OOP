package DO_AN.OOP.exception;

import DO_AN.OOP.dto.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
        // Xử lý các exception kiểu RuntimeException
    ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = ApiResponse.builder().build();
        apiResponse.setCode(500);
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.status(500).body(apiResponse);
    }
}
