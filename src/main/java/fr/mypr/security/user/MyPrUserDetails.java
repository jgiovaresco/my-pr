package fr.mypr.security.user;

import fr.mypr.user.model.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;

@Getter
@ToString
public class MyPrUserDetails extends User
{
	private Long id;
	private String firstName;
	private String lastName;
	private Role role;

	public MyPrUserDetails(String username, String password,
	                       Collection<? extends GrantedAuthority> authorities)
	{
		super(username, password, authorities);
	}


	public static Builder builder()
	{
		return new Builder();
	}


	public static class Builder
	{
		private String username;
		private String password;
		private Set<GrantedAuthority> authorities;

		private Long id;
		private String firstName;
		private String lastName;
		private Role role;


		private Builder()
		{
			authorities = new HashSet<>();
		}

		public Builder id(Long id)
		{
			this.id = id;
			return this;
		}

		public Builder firstName(String firstName)
		{
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName)
		{
			this.lastName = lastName;
			return this;
		}

		public Builder role(Role role)
		{
			this.role = role;

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
			this.authorities.add(authority);

			return this;
		}

		public Builder username(String username)
		{
			this.username = username;
			return this;
		}

		public Builder password(String password)
		{
			this.password = password;
			return this;
		}

		public MyPrUserDetails build()
		{
			MyPrUserDetails user = new MyPrUserDetails(username, password, authorities);
			user.id = id;
			user.firstName = firstName;
			user.lastName = lastName;
			user.role = role;
			return user;
		}
	}
}
