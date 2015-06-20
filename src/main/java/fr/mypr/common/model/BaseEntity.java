package fr.mypr.common.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity<ID>
{
	@Version
	private long version;

	public abstract ID getId();

	public long getVersion()
	{
		return version;
	}
}
