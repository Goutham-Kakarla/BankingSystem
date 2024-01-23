package Entity;

import java.util.Date;

public class Incidents {
    private int incidentID;
    private String incidentType;
    private Date incidentDate;
    private String location;
    private String descriptions;
    private String statuss;
    private int victimID;
    private int suspectID;

    public Incidents() {
        super();
    }

    public Incidents(int incidentID, String incidentType, Date incidentDate, String location,
                     String descriptions, String statuss, int victimID, int suspectID) {
        this.incidentID = incidentID;
        this.incidentType = incidentType;
        this.incidentDate = incidentDate;
        this.location = location;
        this.descriptions = descriptions;
        this.statuss = statuss;
        this.victimID = victimID;
        this.suspectID = suspectID;
    }

    public int getIncidentID() {
        return incidentID;
    }

    public void setIncidentID(int incidentID) {
        this.incidentID = incidentID;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public Date getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(Date incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuses) {
        this.statuss = statuses;
    }

    public int getVictimID() {
        return victimID;
    }

    public void setVictimID(int victimID) {
        this.victimID = victimID;
    }

    public int getSuspectID() {
        return suspectID;
    }

    public void setSuspectID(int suspectID) {
        this.suspectID = suspectID;
    }

    @Override
    public String toString() {
        return "Incidents{" +
                "IncidentID=" + incidentID +
                ", IncidentType='" + incidentType + '\'' +
                ", IncidentDate=" + incidentDate +
                ", Location='" + location + '\'' +
                ", Descriptions='" + descriptions + '\'' +
                ", Statuss='" + statuss + '\'' +
                ", VictimID=" + victimID +
                ", SuspectID=" + suspectID +
                '}';
    }
}
