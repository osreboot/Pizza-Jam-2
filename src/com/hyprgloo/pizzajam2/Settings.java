package com.hyprgloo.pizzajam2;

import java.io.Serializable;

public class Settings implements Serializable{
	private static final long serialVersionUID = -7468554832525797263L;
	
	public boolean soundEnabled = true;
	public boolean musicEnabled = true;
	public boolean tutorialsEnabled = true;
	
	public boolean tut1Complete = false;
	public boolean tut2Complete = false;
	public boolean tut3Complete = false;
	public boolean tut4Complete = false;
	public boolean tut5Complete = false;
	
	public Settings(){}
	
}
