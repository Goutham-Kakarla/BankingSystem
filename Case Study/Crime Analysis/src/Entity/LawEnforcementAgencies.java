package Entity;

import java.util.List;

public class LawEnforcementAgencies {
    private int agencyID;
    private String agencyName;
    private String jurisdiction;
    private String contactInformation;
    private List<Officers> officers;

    public LawEnforcementAgencies(int agencyID, String agencyName, String jurisdiction, String contactInformation,List<Officers> officers) 
    {
        this.agencyID = agencyID;
        this.agencyName = agencyName;
        this.jurisdiction = jurisdiction;
        this.contactInformation = contactInformation;
        this.officers = officers;
    }

    public int getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(int agencyID) {
        this.agencyID = agencyID;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public List<Officers> getOfficers() {
        return officers;
    }

    public void setOfficers(List<Officers> officers) {
        this.officers = officers;
    }
}
