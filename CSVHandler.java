import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {
  private static final String FILE_PATH = "employees.csv";

    public List<Employee> loadEmployees() {
        List<Employee> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] segments = line.split(",");
                if (segments.length == 5) {
                    list.add(new Employee(
                        segments[0].trim(),
                        segments[1].trim(),
                        Double.parseDouble(segments[2].trim()),
                        Double.parseDouble(segments[3].trim()),
                        Double.parseDouble(segments[4].trim())
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error importing CSV file data: " + e.getMessage());
        }
        return list;
    }

    public void saveEmployees(List<Employee> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Employee emp : list) {
                pw.println(emp.toString());
            }
        } catch (IOException e) {
            System.err.println("Error exporting CSV data: " + e.getMessage());
        }
    }
}  

