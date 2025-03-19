
package HospitalManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

        public class Patient {
            private Connection connection;
            private Scanner scanner;

            // Constructor to initialize database connection and scanner
            public Patient(Connection connection, Scanner scanner) {
                this.connection = connection;
                this.scanner = scanner;
            }

            // Method to add a new patient
            public void addPatient() {
                System.out.print("Enter Patient Name: ");
                String name = scanner.next();
                System.out.print("Enter Patient Age: ");
                int age = scanner.nextInt();
                System.out.print("Enter Patient Gender: ");
                String gender = scanner.next();

                try {
                    String query = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, age);
                    preparedStatement.setString(3, gender);

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Patient added successfully!");
                    } else {
                        System.out.println("Failed to add patient.");
                    }

                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Method to view all patients
            public void viewPatients() {
                String query = "SELECT * FROM patients";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    System.out.println("\nPatients:");
                    System.out.println("-----------------------------------------------------");
                    System.out.println("| Patient ID |      Name      |  Age  |  Gender     |");
                    System.out.println("-----------------------------------------------------");

                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        int age = resultSet.getInt("age");
                        String gender = resultSet.getString("gender");

                        System.out.printf("| %-10d | %-14s | %-4d | %-10s |\n", id, name, age, gender);
                        System.out.println("-----------------------------------------------------");
                    }

                    resultSet.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // Method to check if a patient exists by ID
            public boolean getPatientById(int id) {
                String query = "SELECT * FROM patients WHERE id = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    boolean exists = resultSet.next(); // Check if any record exists
                    resultSet.close();
                    preparedStatement.close();
                    return exists;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }
