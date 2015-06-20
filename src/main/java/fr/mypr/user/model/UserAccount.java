package fr.mypr.user.model;

import fr.mypr.common.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Entity
@Table(name = "user_accounts")
public class UserAccount extends BaseEntity<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;

	@Column(name = "first_name", length = 100, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 100, nullable = false)
	private String lastName;

	@Column(name = "password", length = 255)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", length = 20, nullable = false)
	private Role role;

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public Long getId()
	{
		return id;
	}

	public static class Builder
	{

		private UserAccount user;

		public Builder()
		{
			user = new UserAccount();
			user.role = Role.ROLE_USER;
		}

		public Builder id(Long id)
		{
			user.id = id;
			return this;
		}

		public Builder email(String email)
		{
			user.email = email;
			return this;
		}

		public Builder firstName(String firstName)
		{
			user.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName)
		{
			user.lastName = lastName;
			return this;
		}

		public Builder password(String password)
		{
			user.password = password;
			return this;
		}

		public UserAccount build()
		{
			return user;
		}

	}
}
