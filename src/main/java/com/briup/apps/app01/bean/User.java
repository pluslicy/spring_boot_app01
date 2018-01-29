package com.briup.apps.app01.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.briup.apps.app01.view.UserView;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;

@JsonRootName("user")
public class User implements Serializable {
	/**
	 * 
	 */
	public interface WithNameView {};
	public interface WithIdView {};
	 
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String gender;
	private Date birth;
	private List<String> phones ;
	public User() {
		
	}
	
	public User(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	public User(Long id, String name, String gender, Date birth) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
	}

	@JsonView(UserView.Summary.class)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@JsonView(UserView.Summary.class)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@JsonView(UserView.Detail.class)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	@JsonView(UserView.Detail.class)
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	@JsonView(UserView.Detail.class)
	//@JsonIgnore
	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}

	
}
