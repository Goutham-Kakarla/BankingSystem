
package Entity;

import java.util.Date;

public class Reports {
    private int reportID;
    private int incidentID;
    private int reportingOfficer;
    private Date reportDate;
    private String reportDetails;
    private String statuss;
	public Reports(int reportID, int incidentID, int reportingOfficer, Date reportDate, String reportDetails,
			String statuss) {
		super();
		this.reportID = reportID;
		this.incidentID = incidentID;
		this.reportingOfficer = reportingOfficer;
		this.reportDate = reportDate;
		this.reportDetails = reportDetails;
		this.statuss = statuss;
	}
	public int getReportID() {
		return reportID;
	}
	public void setReportID(int reportID) {
		this.reportID = reportID;
	}
	public int getIncidentID() {
		return incidentID;
	}
	public void setIncidentID(int incidentID) {
		this.incidentID = incidentID;
	}
	public int getReportingOfficer() {
		return reportingOfficer;
	}
	public void setReportingOfficer(int reportingOfficer) {
		this.reportingOfficer = reportingOfficer;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public String getReportDetails() {
		return reportDetails;
	}
	public void setReportDetails(String reportDetails) {
		this.reportDetails = reportDetails;
	}
	public String getStatuss() {
		return statuss;
	}
	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}

    // Constructors
    

    // Getters and setters
}