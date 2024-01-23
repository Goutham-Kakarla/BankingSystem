package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Entity.Incidents;
import dao.ICrimeAnalysisServiceImpl;

public class prayogam {
	
	private ICrimeAnalysisServiceImpl prayog;
    
   
    
    @Before
    public void setUp() {
        prayog = new ICrimeAnalysisServiceImpl();
       
        
    }
    
    
    @After
    public void tearDown() {
        prayog = null;
       
       
    }
    
    @Test
    public void testCreateIncident() {
    	
    	Incidents testnewincident = new Incidents(
    			13,
    			"theft",
    			java.sql.Date.valueOf("2023-10-15"),
    			"Latitude: 40, Longitude: 50",
    			"Theft at Bus stop",
    			"Open",
    			2,
    			2
    			);
    	boolean result = prayog.createIncident(testnewincident);
    	
    	assertTrue("Incident creation success", result);
    	
    }
    
    @Test
    public void updateIncidentStatus() {
    	int incidentId = 1;
        String statuss = "Open";
    	
    	boolean result = prayog.updateIncidentStatus(incidentId, statuss);
    	
    	assertTrue("Incident status updated successfully", result);
        
        
    	
    }

}