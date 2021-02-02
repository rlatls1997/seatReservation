package net.skhu.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import net.skhu.entity.User;

@Data
public class SrUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;

	final boolean accountNonExpired = true;
	final boolean accountNonLocked = true;
	final boolean credentialsNonExpired = true;
	final String password;
	final String username;
	final boolean isEnabled;
	Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

	final String name;
	final String email;
	final String typeId;

	public SrUserDetails(User user) {
		switch(user.getTypeId()) {
		case "0" : authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); break;
		case "1" : authorities.add(new SimpleGrantedAuthority("ROLE_USER")); break;
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_LOGINCHECK"));
		this.username = user.getUserId();
		this.password = user.getPassword();
		this.isEnabled = user.isEnabled();

		this.name = user.getName();
		this.email = user.getEmail();
		this.typeId = user.getTypeId();
	}

}
