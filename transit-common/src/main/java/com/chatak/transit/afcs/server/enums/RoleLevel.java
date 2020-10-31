package com.chatak.transit.afcs.server.enums;

public enum RoleLevel {
	
	SUPER_ADMIN("SUPER_ADMIN"),
	ORG_ADMIN("ORG_ADMIN"),
	PTO_ADMIN("PTO_ADMIN");
	
	private String value;
	 
	private RoleLevel(){
		
	}
    private RoleLevel(String s){
		
		this.value=s;
		
	}
	public String getValue(){
		return value;
	}

}