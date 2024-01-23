package Entity;

public class Evidence {
    private int evidenceID;
    private String descriptions;
    private String locationFound;
    private int incidentID;
    
	public Evidence(int evidenceID, String descriptions, String locationFound, int incidentID) {
		super();
		this.evidenceID = evidenceID;
		this.descriptions = descriptions;
		this.locationFound = locationFound;
		this.incidentID = incidentID;
	}

	public int getEvidenceID() {
		return evidenceID;
	}

	public void setEvidenceID(int evidenceID) {
		this.evidenceID = evidenceID;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getLocationFound() {
		return locationFound;
	}

	public void setLocationFound(String locationFound) {
		this.locationFound = locationFound;
	}

	public int getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(int incidentID) {
		this.incidentID = incidentID;
	}

}
