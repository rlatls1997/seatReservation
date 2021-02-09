package net.skhu.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import net.skhu.entity.User;
import net.skhu.model.UserSignIn;
import net.skhu.model.UserSignUp;
import net.skhu.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;
	@Autowired
	public PasswordEncoder passwordEncoder;
	@Autowired
	public ModelMapper modelMapper;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public boolean hasErrors(UserSignUp userSignUp, BindingResult bindingResult) {
		// spring validation에서의 에러 처리
		if (bindingResult.hasErrors())
			return true;

		/* 로직 에러 처리 */
		// 비밀번호 불일치
		if (userSignUp.getPassword().equals(userSignUp.getPasswordCheck()) == false) {
			bindingResult.rejectValue("passwd2", null, "비밀번호가 일치하지 않습니다.");
			return true;
		}

		// 아이디 중복
		User user = userRepository.findByUserId(userSignUp.getUserId());
		if (user != null) {
			bindingResult.rejectValue("userId", null, "중복된 아이디입니다.");
			return true;
		}
		return false;

	}

	public User createEntity(UserSignUp userSignUp) {
		User user = new User();
		user.setUserId(userSignUp.getUserId());
		user.setPassword(passwordEncoder.encode(userSignUp.getPassword()));
		user.setName(userSignUp.getName());
		user.setEmail(userSignUp.getEmail());
		user.setTypeId("1");
		user.setEnabled(true);

		return user;
	}

	public void save(UserSignUp userSignUp) {

		User user = createEntity(userSignUp);
		userRepository.save(user);
		return;
	}

	public boolean hasErrorsInLogin(UserSignIn userSignIn, BindingResult bindingResult) {
		String checkId = userSignIn.getUserId();
		String checkPassword = userSignIn.getPassword();

		if (bindingResult.hasErrors())
			return true;

		// 아이디없음
		User user = userRepository.findByUserId(checkId);
		if (user == null) {
			bindingResult.rejectValue("userId", null, "존재하지 않는 아이디입니다.");
			return true;
		}

		//비밀번호 불일치
		else if(user.getPassword().equals(checkPassword) == false) {
			bindingResult.rejectValue("passWord", null, "비밀번호가 일치하지 않습니다.");
			return true;
		}
		return false;
	}
}
