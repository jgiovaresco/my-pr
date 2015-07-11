package fr.mypr.identityaccess.domain.persistence;

import fr.mypr.common.persistence.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_account")
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

	@Override
	public Long getId()
	{
		return id;
	}
}
