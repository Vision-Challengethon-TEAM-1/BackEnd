package com.vision_hackathon.cheollian.auth.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginSuccessController {

	@ResponseBody
	@GetMapping("/login/success")
	public String loginSuccess() {
		return "Login Successed!";
	}
}
