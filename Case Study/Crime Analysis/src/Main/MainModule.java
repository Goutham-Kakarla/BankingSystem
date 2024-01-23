package Main;

import Entity.Incidents;
import c.exception.IncidentNumberNotFoundException;
import dao.ICrimeAnalysisService;
import dao.ICrimeAnalysisServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainModule {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static ICrimeAnalysisService icrimeanalysisService = new ICrimeAnalysisServiceImpl();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                displayMainMenu();
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createIncident(scanner);
                        break;
                    case 2:
                        updateIncidentStatus(scanner);
                        break;
                    case 3:
                        getIncidentsInDateRange(scanner);
                        break;
                    case 4:
                        searchIncidentsByType(scanner);
                        break;
                    case 5:
                        generateIncidentReport(scanner);
                        break;
                    case 6:
                        exitProgram();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            }
        }
    }

    private static void displayMainMenu() {
    	System.out.println("\n----- Crime Analysis and Reporting System (C.A.R.S.)-----");
        System.out.println("\n-------Main Menu-------\n");
        System.out.println("1. Create Incident");
        System.out.println("2. Update Incident Status");
        System.out.println("3. Get Incidents in Date Range");
        System.out.println("4. Search Incidents");
        System.out.println("5. Generate Incident Report");
        System.out.println("6. Exit");
        System.out.print("\nEnter your choice: ");
    }

    private static void createIncident(Scanner scanner) {
        Incidents newIncident = new Incidents();
        System.out.println("Enter IncidentID: ");
        int incidentId = scanner.nextInt();
        scanner.nextLine();
        newIncident.setIncidentID(incidentId);
        System.out.print("Enter Incident Type: ");
        newIncident.setIncidentType(scanner.next());
        System.out.print("Enter Incident Date (yyyy-MM-dd): ");
        String incidentDateStr = scanner.next();
        try {
            Date incidentDate = dateFormat.parse(incidentDateStr);
            newIncident.setIncidentDate(incidentDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }
        System.out.println("Enter Location: ");
        scanner.nextLine();
        newIncident.setLocation(scanner.nextLine());
        System.out.print("Enter Description: ");
        newIncident.setDescriptions(scanner.nextLine());
        System.out.print("Enter Status: ");
        newIncident.setStatuss(scanner.nextLine());
        System.out.print("Enter Victim ID: ");
        newIncident.setVictimID(scanner.nextInt());
        System.out.print("Enter Suspect ID: ");
        newIncident.setSuspectID(scanner.nextInt());
        boolean incidentCreated = icrimeanalysisService.createIncident(newIncident);
        if (incidentCreated) {
            System.out.println("Incident created successfully.");
        } else {
            System.out.println("Failed to create incident.");
        }
    }

    private static void updateIncidentStatus(Scanner scanner) {
        try {
            System.out.print("Enter Incident ID for status update: ");
            int incidentIdForUpdate = scanner.nextInt();
            System.out.print("Enter new status: ");
            String newStatus = scanner.next();
            boolean statusUpdated = icrimeanalysisService.updateIncidentStatus(incidentIdForUpdate, newStatus);
            if (statusUpdated) {
                System.out.println("Incident status updated successfully.");
            } else throw new IncidentNumberNotFoundException();
        } catch (IncidentNumberNotFoundException e) {
            System.out.println(e);
        }
    }

    private static void getIncidentsInDateRange(Scanner scanner) {
        System.out.print("Enter start date (yyyy-MM-dd): ");
        String startDateString = scanner.next();
        System.out.print("Enter end date (yyyy-MM-dd): ");
        String endDateString = scanner.next();
        try {
            List<Incidents> incidentsList = icrimeanalysisService.getIncidentsInDateRange(startDateString, endDateString);
            if (incidentsList.isEmpty()) {
                System.out.println("No incidents found within the specified date range.");
            } else {
                System.out.println("Incidents within the date range:");
                for (Incidents incident : incidentsList) {
                    System.out.println(incident);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchIncidentsByType(Scanner scanner) {
        try {
            System.out.println("Enter Incident Type to Search: ");
            String incidentType = scanner.next();
            List<Incidents> incidentsList = icrimeanalysisService.searchIncidentsByType(incidentType);
            if (incidentsList.isEmpty()) {
                System.out.println("No incidents found for the specified type.");
            } else {
                System.out.println("Incidents with the specified type:");
                for (Incidents incident : incidentsList) {
                    System.out.println(incident);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
	private static void generateIncidentReport(Scanner scanner) {
        System.out.print("Enter Incident ID: ");
        int incidentIdForReport = scanner.nextInt();
        Incidents incidentForReport = icrimeanalysisService.getIncidentById(incidentIdForReport);

        if (incidentForReport != null) {
            System.out.println("Incident Details:");
            System.out.println("Incident ID: " + incidentForReport.getIncidentID());
            System.out.println("Type: " + incidentForReport.getIncidentType());
            System.out.println("Date: " + incidentForReport.getIncidentDate());

            System.out.print("Enter Reporting Officer ID: ");
            int reportingOfficerId = scanner.nextInt();

            Object generatedReport = icrimeanalysisService.generateIncidentReport(incidentForReport, reportingOfficerId);

            if (generatedReport != null) {
                Map<String, Object> reportDetails = (Map<String, Object>) generatedReport;
                System.out.println("Incident Report Generated Successfully. Report Details:");
                System.out.println("Report ID: " + reportDetails.get("reportID"));
                System.out.println("Report Date: " + reportDetails.get("reportDate"));
                System.out.println("Status: " + reportDetails.get("statuss"));
                System.out.println("Report Details:\n" + reportDetails.get("reportDetails"));
            } else {
                System.out.println("Failed to generate incident report. Please check the input details.");
            }
        } else {
            //System.out.println("Incident not found for the specified Incident ID.");
        }
    }

    private static void exitProgram() {
        System.out.println("Exiting Crime Analysis and Reporting System. Goodbye!");
        System.exit(0);
    }
}
