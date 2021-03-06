package net.skhu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.dto.UserSignUp;
import net.skhu.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;


	/**************회원가입*************/
	@GetMapping("signup")
	public String signUp(Model model, HttpServletRequest request) {
		if(request.isUserInRole("ROLE_LOGINCHECK"))
			return "redirect:/board";
		model.addAttribute(new UserSignUp());
		return "user/signup";
	}

	@PostMapping("signup")
	public String signUp(Model model,
			@Valid UserSignUp userSignUp, BindingResult bindingResult) {
		if(userService.hasErrors(userSignUp, bindingResult)) {
			return "user/signIn";
		}
		userService.save(userSignUp);
		return "user/index";
	}

	@RequestMapping("success")
	public String success() {
		return "user/success";
	}

	/**************로그인*************/
	@RequestMapping("/signin")
	public String signIn(Model model, HttpServletRequest request) {
		if(request.isUserInRole("ROLE_LOGINCHECK"))
			return "redirect:/board";
		return "user/signIn";
	}

	/*
	@PostMapping("/signin")
	public String signIn(Model model, @Valid UserSignIn userSignIn, BindingResult bindingResult) {
		if(userService.hasErrorsInLogin(userSignIn,  bindingResult)){
			return "user/signIn";
		}
		return "user/index";
	}
	*/

	@RequestMapping("/index")
	public String index() {
		return "user/index";
	}
}

