package net.teilin.hackathon24.lib;

public class DigipostUser {
	private String PID;
	private String password;
	
	public DigipostUser(String PID, String password){
		this.PID = PID;
		this.password = password;
	}
	
	public String getPID() {
		// TODO Auto-generated method stub
		return this.PID;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return this.PID + " " +this.password;
	}

}
