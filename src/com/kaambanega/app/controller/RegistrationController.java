package com.kaambanega.app.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kaambanega.app.formbean.RegisterFormBean;
import com.kaambanega.app.service.RegistrationService;
import com.kaambanega.logForDebug.CommonMember;

@Controller
public class RegistrationController {
	RegistrationService service = new RegistrationService();
	
	@RequestMapping(value="/register")
	public ModelAndView register(){
		return new ModelAndView("register");
	}
	
	@RequestMapping(value="/register/checkAvailability", method = RequestMethod.POST)
	public ModelAndView checkAvailability(@RequestParam(value="user_id") String username){
		ModelAndView mv = new ModelAndView("stub");
		try {
			String available = service.checkAvailability(username);
			if(available == null){
				mv.addObject("available","Available");
			}
			else {
				mv.addObject("available","Not Available");
			}
		} catch (ClassNotFoundException e) {
			CommonMember.appendLogFileException("ClassNotFoundException in RegistrationController :: ", e, false);
		}	
		catch(SQLException e){
			CommonMember.appendLogFileException("SQLException in RegistrationController :: ", e, false);
		}
		return mv;
	}
	
	@RequestMapping(value="/register/newUser", method = RequestMethod.POST)//, headers = {"Content-Type: application/x-www-form-urlencoded"})
	public ModelAndView newUser(
			//@RequestBody RegisterFormBean rfb){
//			@RequestParam(value="user_id", required=false) String user_id,
//			@RequestParam(value="password", required=false) String password,
//			@RequestParam(value="email_id", required=false) String email_id,
//			@RequestParam(value="user_typ", required=false) String user_typ,
//			@RequestParam(value="is_actv", required=false) String is_actv){
			//HttpServletRequest request,
			@ModelAttribute RegisterFormBean rfb ){
		ModelAndView mv = new ModelAndView("stub");
//		RegisterFormBean rfb = new RegisterFormBean();
//		rfb.setUser_id(user_id);
//		rfb.setEmail_id(email_id);
//		rfb.setPassword(password);
//		rfb.setUser_typ(user_typ);
//		rfb.setIs_actv(is_actv);
		try {
			if(service.newUser(rfb).equalsIgnoreCase("success")){
				mv.addObject("available", "User Added Successfully");
				return mv;
			}
			else {
				mv.addObject("available","There was a problem adding the user");
				return mv;
			}
		} catch (ClassNotFoundException e) {
			CommonMember.appendLogFileException("ClassNotFoundException in RegistrationController :: ", e, false);
		}	
		catch(SQLException e){
			CommonMember.appendLogFileException("SQLException in RegistrationController :: ", e, false);
		}
		return mv;
	}
	
}
