package com.blsa.ezilog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blsa.ezilog.model.BasicResponse;
import com.blsa.ezilog.model.user.LoginRequestDTO;
import com.blsa.ezilog.model.user.SignupRequestDTO;
import com.blsa.ezilog.model.user.UpdateRequestDTO;
import com.blsa.ezilog.model.user.User;
import com.blsa.ezilog.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user/signup")
	@ApiOperation(value="회원 가입",notes="닉네임 중복시 status=false 및 data='nickname', 이메일 중복시 status=false 및 data='email', 정상일경우 status=true 및 data='success' 반환")
	public Object signup(@Valid @RequestBody SignupRequestDTO request) {
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		String check = userService.duplicateCheck(request.getEmail(),request.getNickname());
		if(check.equals("email")) {
			result.status = false;
			result.data="email";
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}else if(check.equals("nickname")) {
			result.status = false;
			result.data="nickname";
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}else {
			User u = userService.signup(request);
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}

		return response;
	}

	@PostMapping("/user/login")
	@ApiOperation(value="로그인",notes="로그인 성공 시 status=true, data='success',object=로그인한 유저 반환, 실패시 status=false,data='fail' 반환")
	public Object login(@Valid @RequestBody LoginRequestDTO request) {
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		User u = userService.login(request);
		if(u!=null) {
			result.status = true;
			result.data = "success";
			result.object = u;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}else {
			result.status = false;
			result.data = "fail";
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@PutMapping("/user/update")
	@ApiOperation(value="회원 정보 수정",notes="정보 수정 성공 시 status=true, data='success' 반환, 닉네임 중복시 status=false 및 data='nickname' 반환")
	public Object update(@Valid @RequestBody UpdateRequestDTO request) {
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		User checkUser = userService.select(request.getUid());
		String checkname = userService.duplicateCheck("", request.getNickname());
		if(!checkUser.getNickname().equals(request.getNickname())&&checkname.equals("nickname")) {
			result.status = false;
			result.data = "nickname";
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

		}else {
			userService.update(request);
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	@GetMapping("/user/detail")
	@ApiOperation(value="회원 정보 조회",notes="조회 성공시 status=true, data='success', object=해당 유저 반환, 실패시 status=false,data='fail' 반환")
	public Object select(@RequestParam long uid) {
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		User user = userService.select(uid);
		if(user==null) {
			result.status = false;
			result.data = "nickname";
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

		}else {
			result.status = true;
			result.data = "success";
			result.object = user;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	@DeleteMapping("/user/delete")
	@ApiOperation(value="회원 탈퇴",notes="탈퇴 성공시 status=true, data='success' 반환")
	public Object withdraw(@RequestParam long uid) {
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();

		userService.withdraw(uid);
		result.status = true;
		result.data = "success";
		response = new ResponseEntity<>(result, HttpStatus.OK);

		return response;
	}
}
