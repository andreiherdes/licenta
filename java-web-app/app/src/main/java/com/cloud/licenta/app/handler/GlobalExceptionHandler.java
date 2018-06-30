package com.cloud.licenta.app.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MultipartException.class)
	public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Oooops!\n" + e.getCause().getMessage());
		return "redirect:/mainpage";
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public String handleError404(HttpServletRequest request, Exception e) {
		return "redirect:/404";
	}
}
