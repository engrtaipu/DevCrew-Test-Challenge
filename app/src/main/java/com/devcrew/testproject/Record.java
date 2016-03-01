package com.devcrew.testproject;

public class Record {

	String fName, lName;

	public Record(String f_name, String l_name) {
		this();
		String fn = setCase(f_name);
		String ln = setCase(l_name);
		this.fName = fn;
		this.lName = ln;
	}

	public Record() {
		// TODO Auto-generated constructor stub
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) { 
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) { 
		this.lName = lName;
	}

	private String setCase(String s) {
		boolean test = Character.isUpperCase(s.charAt(0));
		if (test) {
			return s;
		} else {
			char re = Character.toUpperCase(s.charAt(0));
			StringBuilder myName = new StringBuilder(s);
			myName.setCharAt(0, re);
			return myName.toString();
		}

	}
}
