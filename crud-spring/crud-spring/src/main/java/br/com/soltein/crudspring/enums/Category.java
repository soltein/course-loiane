package br.com.soltein.crudspring.enums;

public enum Category {
	BACKE_END("Back-End"), FRONT_END("Front-End");
	private String value;
	
	private Category(String value){
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
