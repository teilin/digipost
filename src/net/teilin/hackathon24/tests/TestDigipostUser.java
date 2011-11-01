package net.teilin.hackathon24.tests;

import net.teilin.hackathon24.lib.DigipostUser;
import android.test.AndroidTestCase;

public class TestDigipostUser extends AndroidTestCase {
	
	private String pid;
	private String password;
	private DigipostUser du;
	
	public TestDigipostUser() {
		
	}
	
	@Override
	protected void setUp() {
		this.pid = "8899";
		this.password = "test1234";
		du = new DigipostUser(this.pid, this.password);
	}
	
	public void testPidShouldBeEqualsToReturnedPid() {
		assertEquals(this.pid,this.du.getPID());
	}
	
	public void testPasswordShouldBeEqualsToReturnedPassword() {
		assertEquals(this.password,this.du.getPassword());
	}
}
