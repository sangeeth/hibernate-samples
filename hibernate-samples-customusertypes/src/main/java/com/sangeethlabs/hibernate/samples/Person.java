package com.sangeethlabs.hibernate.samples;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private Gender gender;
	
	private Occupation occupation;
	
	private boolean alive;

	public Person() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
	
}
