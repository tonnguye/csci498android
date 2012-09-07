package csci498.tonnguye.lunchlist;

import java.util.Date;

public class Restaurant {
	
	private String name = "";
	private String address = "";
	private String type = "";
	private Date date;
	
	public String toString(){
		return getName();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
