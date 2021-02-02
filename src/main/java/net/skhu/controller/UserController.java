package net.skhu.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.model.UserSignIn;
import net.skhu.model.UserSignUp;
import net.skhu.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;


	/**************회원가입*************/
	@GetMapping("signup")
	public String signUp(Model model) {
		model.addAttribute(new UserSignUp());
		return "user/signUp";
	}

	@PostMapping("signup")
	public String signUp(Model model,
			@Valid UserSignUp userSignUp, BindingResult bindingResult) {
		if(userService.hasErrors(userSignUp, bindingResult)) {
			return "user/signUp";
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
	public String signIn() {
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

