package code;

public class Location {
	
	public String codename;
	public String agent;
	public boolean revealed;
	
	public Location(String cd, String p) {
		codename = cd;
		agent = p;
		revealed = false;
	}
}
