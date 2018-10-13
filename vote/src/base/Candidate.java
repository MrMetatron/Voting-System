package base;

import java.io.Serializable;

public class Candidate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9217017129501080524L;
	private String name = "";
	private String sex = "";
	private String speciality = "";
	private String info = "";
	private int poll = 0; 
	
	public String getName() {
		return name;
	}
	
	public String getSex() {
		return sex;
	}
	
	public String getSpeciality() {
		return speciality;
	}
	
	public String getInfo() {
		return info;
	}
	
	public int getPoll() {
		return poll;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public void setPoll(int polls) {
		this.poll = polls;
	}
}
