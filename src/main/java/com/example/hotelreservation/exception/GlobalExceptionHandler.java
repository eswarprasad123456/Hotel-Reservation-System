package com.example.hotelreservation.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleUnexpectedException(Exception ex) {
		
	    log.error("Unexpected error occurred", ex);
	    
	    Map<String, String> response = new HashMap<>();
	    
	    response.put("error", "An unexpected server error occurred");
	    
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, String>>handleCustomerNotFound(CustomerNotFoundException ex) {
    	
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Map<String, String>>handleRoomNotFound(RoomNotFoundException ex) {
    	
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    
    @ExceptionHandler(RoomTypeNotFoundException.class)
    public ResponseEntity<Map<String, String>>handleRoomTypeNotFound(RoomTypeNotFoundException ex) {
    	
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Map<String, String>>handleReservationNotFound(ReservationNotFoundException ex) {
    	
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Map<String, String>>handlePaymentNotFound(PaymentNotFoundException ex) {
    	
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    
    @ExceptionHandler(AmenityNotFoundException.class)
    public ResponseEntity<Map<String, String>>handleAmenityNotFound(AmenityNotFoundException ex) {
    	
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, String>>handleDuplicate(DuplicateResourceException ex) {
    	
        return createResponse(HttpStatus.CONFLICT, ex.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>handleValidation(MethodArgumentNotValidException ex) {
    	
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error ->errors.put(error.getField(), error.getDefaultMessage()));
        
        return ResponseEntity.badRequest().body(errors);
    }
    
    private ResponseEntity<Map<String, String>>createResponse(HttpStatus status, String message) {
    	
        Map<String, String> response = new HashMap<>();
        
        response.put("error", message);
        
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>>handleIllegalArgument(IllegalArgumentException ex) {

        Map<String, String> response = new HashMap<>();

        response.put("error", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}
