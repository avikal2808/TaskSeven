import java.sql.*;
import java.util.Scanner;

public class TaskSeven {

    // Database connection details (Change according to your setup)
    private static final String URL = "jdbc:mysql://localhost:3306/companydb";
    private static final String USER = "root";   // your MySQL username
    private static final String PASSWORD = "root"; // your MySQL password

    private Connection conn;

    public TaskSeven() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to Database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Add Employee ---
    public void addEmployee(String name, String department, double salary) {
        String sql = "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, department);
            stmt.setDouble(3, salary);
            stmt.executeUpdate();
            System.out.println("✅ Employee added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- View Employees ---
    public void viewEmployees() {
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n📋 Employee Records:");
            System.out.println("ID | Name | Department | Salary");
            System.out.println("-------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("department") + " | " +
                        rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Update Employee ---
    public void updateEmployee(int id, String newDept, double newSalary) {
        String sql = "UPDATE employees SET department = ?, salary = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newDept);
            stmt.setDouble(2, newSalary);
            stmt.setInt(3, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Employee updated successfully!");
            } else {
                System.out.println("⚠️ Employee not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Delete Employee ---
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("🗑 Employee deleted successfully!");
            } else {
                System.out.println("⚠️ Employee not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Main menu ---
    public static void main(String[] args) {
        TaskSeven app = new TaskSeven();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Employee Database Menu ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter department: ");
                    String dept = scanner.nextLine();
                    System.out.print("Enter salary: ");
                    double salary = scanner.nextDouble();
                    app.addEmployee(name, dept, salary);
                    break;

                case 2:
                    app.viewEmployees();
                    break;

                case 3:
                    System.out.print("Enter employee ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new department: ");
                    String newDept = scanner.nextLine();
                    System.out.print("Enter new salary: ");
                    double newSalary = scanner.nextDouble();
                    app.updateEmployee(updateId, newDept, newSalary);
                    break;

                case 4:
                    System.out.print("Enter employee ID to delete: ");
                    int deleteId = scanner.nextInt();
                    app.deleteEmployee(deleteId);
                    break;

                case 5:
                    System.out.println("👋 Exiting...");
                    return;

                default:
                    System.out.println("⚠️ Invalid choice. Try again!");
            }
        }
    }
}
