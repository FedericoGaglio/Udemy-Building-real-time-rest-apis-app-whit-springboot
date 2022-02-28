package com.springboot.blog.exception;

import com.springboot.blog.payload.ErrorDetails;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    // handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    // global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                               WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /*Con questo metodo che segue andando ad esempio a fare la create di un post, invece
     * che dare semplicemnte un 500 internal error, viene restituito per ogni campo sbaglioato la problematica
     * specifica. Esempio di errore con customizzazione effettuata grazie a questo metodo che segue:
     * 
     * REQUEST:
     * {
	 *	    "title": "t", // deve avere almeno due caratteri, ne ha uno
	 *	    "description": "", // n on dovrebbe essere nullo
	 *	    "content":"" //non dovrebbe essere nullo
	 *  }
     * NB: la customizzazione in risposta riprende la validazione effettuata per campi nella classe
     * postDto attraverso l'utilizzo di opportune annotazione
     * 
     * 
     * RESPONSE:
     * 
     * {
	 *	    "description": "non deve essere vuoto",
	 *	    "title": "Il post deve avere almeno due caratteri!",
	 *	    "content": "non deve essere vuoto"
	 *	}
	 *
	 *Come si può notare per ogni campo viene riportato il problema in  relazione al campo stesso (indicando
	 *per ognugno la problematica in maniera specifica).
     * 
     * 
     * */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)  -> {
			String fieldName = ((FieldError)error).getField();
			String message= error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
    
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<Object> handleMethodArgumentNotValidException(
//			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//		Map<String, String> errors = new HashMap<>();
//		
//		exception.getBindingResult().getAllErrors().forEach((error)  -> {
//			String fieldName = ((FieldError)error).getField();
//			String message= error.getDefaultMessage();
//			errors.put(fieldName, message);
//		});
//		
//		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//	}
}
