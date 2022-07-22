package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.exception.AuthenticationException;
import com.example.form.AddSavingTargetForm;
import com.example.response.AddSavingTargetResponse;
import com.example.service.SavingTargetService;

@RestController
@RequestMapping("/savingTarget")
public class SavingTargetController {

	@Autowired
	private SavingTargetService savingTargetService;

	/**
	 * 収支を登録
	 * @throws Throwable 
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/addSavingTarget")
	public AddSavingTargetResponse addSavingTarget(@RequestBody AddSavingTargetForm form) throws Throwable {
		return savingTargetService.addSavingTarget(form);
	}

}
