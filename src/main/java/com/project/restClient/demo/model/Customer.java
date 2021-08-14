package com.project.restClient.demo.model;

public class Customer {

	private String id;

	private String firstName;

	private String lastName;

	private String email;

	private Long version;

	public Customer() {}

	public String getId() { return id; }

	public void setId(String id) { this.id = id; }

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public Long getVersion() { return version; }

	public void setVersion(Long version) { this.version = version; }

	@Override
	public String toString() {
		return "Customer [id=" + getId() + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", version="
				+ version + "]";
	}

}
