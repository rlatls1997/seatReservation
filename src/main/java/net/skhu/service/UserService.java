package net.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import net.skhu.entity.User;
import net.skhu.model.UserSignUp;
import net.skhu.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;

	public List<User> findAll(){
		return userRepository.findAll();
	}

	public boolean hasErrors(UserSignUp userSignUp, BindingResult bindingResult) {
		//spring validation에서의 에러 처리
		if(bindingResult.hasErrors())
			return true;

		/*로직 에러 처리*/
		//비밀번호 불일치
		if(userSignUp.getPasswd1().equals(userSignUp.getPasswd2()) == false) {
			bindingResult.rejectValue("passwd2", null, "비밀번호가 일치하지 않습니다.");
			return true;
		}

		//아이디 중복
		User user = userRepository.findByUserId(userSignUp.getUserId());
		if(user != null) {
			bindingResult.rejectValue("userId", null, "중복된 아이디입니다.");
			return true;
		}
		return false;


	}

	public User createEntity(UserSignUp userSignUp) {
		User user = new User();
		user.setUserId(userSignUp.getUserId());
		user.setPassword(userSignUp.getPasswd1());
		user.setName(userSignUp.getName());
		user.setEmail(userSignUp.getEmail());
		user.setTypeId("1");

		return user;
	}

	public void save(UserSignUp userSignUp) {
		User user = createEntity(userSignUp);

		userRepository.save(user);
	}
}















