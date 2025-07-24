package Methods;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AddToExcel {
    // Generate filename with timestamp
    private static String getFilename() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);
        return "BookingResults_" + timestamp + ".xlsx";
    }
    
    // Method to add holiday homes data to Excel
    public static void addSheetHomes(List<List<String>> homeList) {
        try {
            // Create new workbook
            Workbook workbook = new XSSFWorkbook();
            
            // Add Holiday Homes sheet
            addHomesSheet(workbook, homeList);
            
            // Save workbook
            saveWorkbook(workbook, getFilename());
            
        } catch (Exception e) {
            System.out.println("Error in addSheetHomes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to add cruise ship details to Excel
    public static void addSheetCruise(List<String> shipDetails) {
        try {
            // Create new workbook
            Workbook workbook = new XSSFWorkbook();
            
            // Add Cruise Line sheet
            addCruiseSheet(workbook, shipDetails);
            
            // Save workbook
            saveWorkbook(workbook, getFilename());
            
        } catch (Exception e) {
            System.out.println("Error in addSheetCruise: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Method to add both holiday homes and cruise data to the same Excel file
    public static void addDataToExcel(List<List<String>> homeList, List<String> shipDetails) {
        try {
            // Create new workbook
            Workbook workbook = new XSSFWorkbook();
            
            // Add Holiday Homes sheet
            addHomesSheet(workbook, homeList);
            
            // Add Cruise Line sheet
            addCruiseSheet(workbook, shipDetails);
            
            // Save workbook with both sheets
            String filename = getFilename();
            saveWorkbook(workbook, filename);
            
        } catch (Exception e) {
            System.out.println("Error in addDataToExcel: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Helper method to add homes sheet to workbook
    private static void addHomesSheet(Workbook workbook, List<List<String>> homeList) {
        // Create sheet for Holiday Homes
        Sheet sheet = workbook.createSheet("Holiday Homes");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        // Updated headers - changed "No. of Stars" to "Rating" and removed "Total Reviews" and "Charge Per Night"
        String[] headers = {"Hotel Name", "Rating", "Number of People", "Total Charge"};
        
        // Set header style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        
        // Add headers
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Add data rows
        int rowNum = 1;
        for (List<String> home : homeList) {
            Row row = sheet.createRow(rowNum++);
            
            // Check if we have all required fields
            if (home.size() >= 4) {  // HomeNavigation.getHomeList returns 4 elements
                // Map the data correctly:
                // 0: name (Hotel Name)
                // 1: rating (Rating)
                // 2: number of people (Number of People)
                // 3: total price (Total Charge)
                
                // Hotel Name
                row.createCell(0).setCellValue(home.get(0));
                
                // Rating
                row.createCell(1).setCellValue(home.get(1));
                
                // Number of People
                row.createCell(2).setCellValue(home.get(2));
                
                // Total Charge
                row.createCell(3).setCellValue(home.get(3));
            } else {
                System.out.println("Warning: Home data incomplete, expected at least 4 fields but got " + home.size());
            }
        }
        
        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    // Helper method to add cruise sheet to workbook
    private static void addCruiseSheet(Workbook workbook, List<String> shipDetails) {
        // Create sheet for Cruise Line
        Sheet sheet = workbook.createSheet("Cruise Line");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        // Updated headers based on requirements
        String[] headers = {"Ship Name", "Brochure Name", "Round Trip", "Port of Call", "Price", "Rating"};
        
        // Set header style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        
        // Add headers
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Add data row
        Row row = sheet.createRow(1);
        
        // Map the data from CruiseNavigation.getInfo():
        // 0: cruise name (Ship Name)
        // 1: brochure name (Brochure Name)
        // 2: departure port (Round Trip)
        // 3: ports of call (Port of Call)
        // 4: price (Price)
        // 5: rating (Rating)
        
        if (shipDetails.size() >= 6) {
            for (int i = 0; i < 6; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(shipDetails.get(i));
            }
        } else {
            System.out.println("Warning: Ship data incomplete, expected 6 fields but got " + shipDetails.size());
        }
        
        // Auto size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    // Helper method to save workbook
    private static void saveWorkbook(Workbook workbook, String fileName) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
            System.out.println("Excel file has been created successfully at: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving workbook: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                System.out.println("Error closing workbook: " + e.getMessage());
            }
        }
    }
    
    // Main method for testing with dummy data
    public static void main(String[] args) {
        // Test with dummy holiday homes data - updated to match new structure
        List<List<String>> dummyHomeList = List.of(
            List.of("Luxury Villa Nairobi", "4.5", "4", "$750"),
            List.of("Garden Apartment", "3.8", "4", "$600"),
            List.of("Downtown Penthouse", "4.2", "4", "$900")
        );
        
        // Test with dummy cruise ship data - updated to match new structure
        List<String> dummyCruiseData = List.of(
            "Symphony of the Seas", // Ship Name
            "Royal Caribbean", // Brochure Name
            "Miami, Florida", // Round Trip
            "Cozumel, Nassau, St. Thomas", // Port of Call
            "$999", // Price
            "4.7" // Rating
        );
        
        // Call the combined method to create a single Excel file with both sheets
        addDataToExcel(dummyHomeList, dummyCruiseData);
        
        System.out.println("Test complete - Excel file created with updated column structure");
        System.out.println("Holiday Homes sheet: Hotel Name, Rating, Number of People, Total Charge");
        System.out.println("Cruise Line sheet: Ship Name, Brochure Name, Round Trip, Port of Call, Price, Rating");
    }
}
