package log.iqpizza6349.springcourierdelivery.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseDto> defaultException(Exception ignored) {
        ResponseDto responseDto = new ResponseDto("서버에 커피를 쏟았습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseDto);
    }

    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<ResponseDto> globalException(GlobalException e) {
        ResponseDto responseDto = new ResponseDto(e.getMessage());
        return ResponseEntity.status(e.getStatus())
                .body(responseDto);
    }

    @Getter
    static class ResponseDto {
        private final LocalDateTime localDateTime;
        private final String message;

        public ResponseDto(String message) {
            this.localDateTime = LocalDateTime.now();
            this.message = message;
        }
    }

}
