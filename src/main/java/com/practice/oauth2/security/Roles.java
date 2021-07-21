package com.practice.oauth2.security;

import java.util.Set;
import com.google.common.collect.Sets;

public enum Roles {
	SAMPLE(Sets.newHashSet("read")), 
	XYZ(Sets.newHashSet("write")), 
	ADMIN(Sets.newHashSet("read", "write"));

	private Set<String> permission;

	Roles(Set<String> permission) {
		this.permission = permission;
	}

	public Set<String> getPermission() {
		return permission;
	}

}
