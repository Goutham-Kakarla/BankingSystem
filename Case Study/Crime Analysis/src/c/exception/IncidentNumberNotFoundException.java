package c.exception;

public class IncidentNumberNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncidentNumberNotFoundException(String string) {
		//System.out.print("Incident ID is not found");
	}

	public IncidentNumberNotFoundException() {
			System.out.print("Incident ID not found Unable to update status");
	}

	
}
