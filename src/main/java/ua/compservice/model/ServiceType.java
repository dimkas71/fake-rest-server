package ua.compservice.model;

import java.util.Arrays;


public enum ServiceType {
	
	ELECTRICITY("Электричество"),
	VIDEO("Видеонаблюдение"),
	SERVICE("Услуги рынка"),
	NONE("Не задана услуга");
	
	
	private String type;
	
	ServiceType(String aType) {
		this.type = aType;
	}
	
	public static ServiceType of(String aType) {
		
		return Arrays.asList(ServiceType.values())
			.stream()
			.filter(st -> st.type.equalsIgnoreCase(aType))
			.findFirst()
			.orElse(ServiceType.NONE);
	}
	
	

}
