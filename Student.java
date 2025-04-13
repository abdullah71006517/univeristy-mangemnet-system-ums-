import java.io.*;
import java.util.*;

public class Student {
    private String name, id, password, program, batch;
    private double cgpa;
    private final Scanner scanner = new Scanner(System.in);

    public void addStudent() {
        System.out.println("Enter student name:");
        name = scanner.nextLine();

        System.out.println("Enter student ID:");
        id = scanner.nextLine();

        if (studentExists(id)) {
            System.err.println(" Student ID already exists.  Try a different one.");
            return;
        }

        System.out.println("Enter program:");
        program = scanner.nextLine();

        System.out.println("Enter batch:");
        batch = scanner.nextLine();

        System.out.println("Enter password:");
        password = scanner.nextLine();

        cgpa = 0.0;

        String line = id + "," + name + "," + program + "," + batch + "," + cgpa + "," + password + "\n";
        writeToFile(line);
    }

    private void writeToFile(String line) {
        try (RandomAccessFile raf = new RandomAccessFile("student.txt", "rw")) {
            raf.seek(raf.length());
            raf.writeBytes(line);
            System.out.println("Student information saved.");
        } catch (IOException e) {
            System.out.println(" Failed to write student info.");
            e.printStackTrace();
        }
    }

    private boolean studentExists(String studentId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("student.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(studentId + ",")) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("error");
        }
        return false;
    }

    public void viewStudentById() {
        System.out.println("Enter student ID to view:");
        String searchId = scanner.nextLine();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("student.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(searchId + ",")) {
                    String[] parts = line.split(",");
                    System.out.println(" Name: " + parts[1].trim());
                    System.out.println(" Program: " + parts[2].trim());
                    System.out.println(" Batch: " + parts[3].trim());
                    System.out.println(" CGPA: " + parts[4].trim());
                    found = true;
                }
            }
            if (!found) System.err.println(" Student ID not found.");
        } catch (IOException e) {
            System.err.println(" Error reading student info.");
            e.printStackTrace();
        }
    }

    public void resetPassword() {
        System.out.println("Enter student ID:");
        String targetId = scanner.nextLine();

        System.out.println("Enter new password:");
        String newPass = scanner.nextLine();

        boolean success = updateLine(targetId, 5, newPass, "Password");
        if (!success) {
            System.err.println(" Student ID not found. Password reset failed.");
        }
    }

    public void updateStudent() {
        System.out.println("Enter student ID to update:");
        String targetId = scanner.nextLine();

        System.out.println("Update name:");
        String newName = scanner.nextLine();

        System.out.println("Update program:");
        String newProgram = scanner.nextLine();

        System.out.println("Update batch:");
        String newBatch = scanner.nextLine();

        boolean nameUpdated = updateLine(targetId, 1, newName, null);
        boolean programUpdated = updateLine(targetId, 2, newProgram, null);
        boolean batchUpdated = updateLine(targetId, 3, newBatch, null);

        if (nameUpdated || programUpdated || batchUpdated) {
            System.out.println(" Student information updated successfully.");
        } else {
            System.out.println(" Student ID not found. Update failed.");
        }
    }

    public void submitResult() {
        System.out.println("Enter student ID:");
        String targetId = scanner.nextLine();

        System.out.println("Enter new semester GPA:");
        double semesterGPA;
        try {
            semesterGPA = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println(" Invalid GPA input.");
            scanner.nextLine();
            return;
        }

        boolean found = false;

        try {
            File file = new File("student.txt");
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(targetId)) {
                    double oldCgpa = Double.parseDouble(parts[4].trim());
                    double newCgpa = (oldCgpa + semesterGPA) / 2;
                    parts[4] = String.format("%.2f", newCgpa);
                    line = String.join(",", parts);
                    found = true;
                }
                writer.write(line + "\n");
            }

            reader.close();
            writer.close();
            file.delete();
            tempFile.renameTo(file);

            if (found) {
                System.out.println(" GPA submitted and CGPA updated.");
            } else {
                System.err.println(" Student ID not found. GPA not submitted.");
            }

        } catch (IOException e) {
            System.err.println(" Error processing GPA submission.");
            e.printStackTrace();
        }
    }

    private boolean updateLine(String id, int fieldIndex, String newValue, String fieldName) {
        boolean updated = false;

        try {
            File inputFile = new File("student.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (parts[0].equals(id)) {
                    parts[fieldIndex] = newValue;
                    updated = true;
                    currentLine = String.join(",", parts);
                }
                writer.write(currentLine + "\n");
            }

            writer.close();
            reader.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);

            if (updated && fieldName != null) {
                System.out.println("- " + fieldName + " updated successfully.");
            }

        } catch (IOException e) {
            System.err.println(" Error updating file.");
            e.printStackTrace();
        }

        return updated;
    }
}
