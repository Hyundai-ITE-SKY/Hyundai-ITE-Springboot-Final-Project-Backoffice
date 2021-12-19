package com.mycompany.webapp.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Component
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	@ExceptionHandler
	public String handleOtherException(Exception e, HttpServletResponse response) throws IOException {
		log.info(e.getMessage());
		e.printStackTrace();
		response.sendError(500);
		return "error/500";
	}
	
	@ExceptionHandler
	public String handle404Exception(NoHandlerFoundException e, HttpServletResponse response) throws IOException {
		log.info(e.getMessage());
		e.printStackTrace();
		response.sendError(404);
		return "error/404";
	}

	@ExceptionHandler
	public String handleBadCredentialsException(BadCredentialsException e, HttpServletResponse response)
			throws IOException {
		log.info(e.getMessage());
		response.sendError(401);
		return "error/401";
	}
}
