package uk.ac.cf.spring.Group13Project1.errors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.Group13Project1.passwordReset.web.PasswordResetController;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleAllExceptions(Exception exc) {
//        ErrorObject error = new ErrorObject(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "An unexpected error occurred. Please contact help desk.",
//                System.currentTimeMillis());
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("status", error.getStatus());
//        modelAndView.addObject("message", error.getMessage());
//        modelAndView.addObject("timeStamp", error.getTimeStamp());
//        modelAndView.setViewName("error/error");
//
//        return modelAndView;
//    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ErrorObject error = new ErrorObject(
                HttpStatus.CONFLICT.value(),
                "Cannot create duplicate entry: " + e.getRootCause().getMessage(),
                System.currentTimeMillis());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("status", error.getStatus());
        modelAndView.addObject("message", error.getMessage());
        modelAndView.addObject("timeStamp", error.getTimeStamp());
        modelAndView.setViewName("error/error");

        return modelAndView;
    }
}
