package util;

public enum Gender {
	MALE, FEMALE;

	public String getLabel() {
		return this.name() == "MALE" ? "Homme" : "Femme";
	}
}

