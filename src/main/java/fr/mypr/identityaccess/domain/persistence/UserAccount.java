package fr.mypr.identityaccess.domain.persistence;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_account")
public class UserAccount
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
}
