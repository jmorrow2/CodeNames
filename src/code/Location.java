package code;

public class Location {
	
	/** codename assigned to the location instance */
	private String codename;
	/** agent assigned to the location instance */
	private String agent;
	/** boolean declaring whether or not the locations agent has been revealed yet */
	private boolean revealed;
	
	/**Instances of this class are used to represent a space on the game board
	 * 
	 * @param cd codename to be assigned to  the location instance
	 * @param a agent to be assigned to the location instance
	 */
	public Location(String cd, String a) {
		codename = cd;
		agent = a;
		revealed = false;
	}
	
	/**
	Getter Method for codename string
	*/
	public String getCodename() {
		return codename;
	}

	/**
	Setter Method for codename string
	*/
	public void setCodename(String codename) {
		this.codename = codename;
	}

	/**
	Getter Method for agent string
	*/
	public String getAgent() {
		return agent;
	}

	/**
	Setter Method for agent string
	*/
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	Getter Method for revealed boolean
	*/
	public boolean getRevealed() {
		return revealed;
	}

	/**
	Setter Method for revealed boolean
	*/
	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}
}
