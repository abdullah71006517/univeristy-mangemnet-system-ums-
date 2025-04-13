import java.io.*;

public class AcademicRecordManager {


    public void submitSemesterGPA(String studentId, String semester, double gpa) {
        String line = studentId + "," + semester + "," + gpa + "\n";
        try (RandomAccessFile raf = new RandomAccessFile("results.txt", "rw")) {
            raf.seek(raf.length());
            raf.writeBytes(line);
            System.out.println("GPA saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save GPA.");
            e.printStackTrace();
        }
    }


    public void viewAllGPA(String studentId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("results.txt"))) {
            String line;
            boolean found = false;
            System.out.println("📘 Semester GPAs for student ID: " + studentId);

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(studentId)) {
                    System.out.println("🔹 " + parts[1] + " ➤ GPA: " + parts[2]);
                    found = true;
                }
            }

            if (!found) {
                System.err.println("No GPA records found.");
            }

        } catch (IOException e) {
            System.out.println(" Failed to read GPA file.");
            e.printStackTrace();
        }
    }


    public void calculateCGPA(String studentId) {
        double total = 0;
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("results.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(studentId)) {
                    total += Double.parseDouble(parts[2]);
                    count++;
                }
            }

            if (count > 0) {
                double cgpa = total / count;
                System.out.printf("🎓 CGPA for student %s: %.2f\n", studentId, cgpa);
            } else {
                System.out.println(" No GPA data found to calculate CGPA.");
            }

        } catch (IOException e) {
            System.out.println(" Failed to read GPA file.");
            e.printStackTrace();
        }
    }
}
