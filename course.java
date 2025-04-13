import java.io.*;
import java.util.Scanner;

public class course {
    private String courseCode, courseName, instructorName, department;
    private int roomNumber, creditHours, maxCapacity;
    Scanner scanner = new Scanner(System.in);

    public void addCourse() {
        System.out.println("Enter course code:");
        courseCode = scanner.nextLine();

        System.out.println("Enter course name:");
        courseName = scanner.nextLine();

        System.out.println("Enter instructor name:");
        instructorName = scanner.nextLine();

        System.out.println("Enter department:");
        department = scanner.nextLine();

        System.out.println("Enter room number:");
        roomNumber = scanner.nextInt();

        System.out.println("Enter credit hours:");
        creditHours = scanner.nextInt();

        System.out.println("Enter student capacity:");
        maxCapacity = scanner.nextInt();
        scanner.nextLine();

        String line = courseCode + "," + courseName + "," + instructorName + "," + department + "," + roomNumber + "," + creditHours + "," + maxCapacity + "\n";

        try (RandomAccessFile raf = new RandomAccessFile("course.txt", "rw")) {
            raf.seek(raf.length());
            raf.writeBytes(line);
            System.out.println(" Course saved.");
        } catch (IOException e) {
            System.err.println("Failed to write course.");
            e.printStackTrace();
        }
    }

    public void assignCourseToStudent() {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();

        System.out.println("Enter course code to assign:");
        String courseCode = scanner.nextLine();

        if (!isValidStudent(studentId)) {
            System.err.println(" Student not found.");
            return;
        }

        if (!isValidCourse(courseCode)) {
            System.err.println(" Course not found.");
            return;
        }

        String line = studentId + "," + courseCode + "\n";
        try (RandomAccessFile raf = new RandomAccessFile("student_courses.txt", "rw")) {
            raf.seek(raf.length());
            raf.writeBytes(line);
            System.out.println(" Course assigned to student.");
        } catch (IOException e) {
            System.err.println(" Failed to assign course.");
            e.printStackTrace();
        }
    }

    public void viewCoursesByStudent() {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("student_courses.txt"))) {
            String line;
            System.out.println("  Courses assigned to student ID " + studentId + ":");
            System.out.println("coursecode-course name-instructor-dept-room-credit-limit");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(studentId)) {
                    printCourseDetails(parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read course assignments.");
            e.printStackTrace();
        }
    }
    public void viewAllCourses() {
        System.out.println("📘 All Available Courses:");
        System.out.println("coursecode - course name - instructor - dept - room - credit - limit");

        try (BufferedReader reader = new BufferedReader(new FileReader("course.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 7) {
                    System.out.println("- " + fields[0] + "  -  " + fields[1] + " - " + fields[2] + " - " + fields[3] + " - " + fields[4] + " - " + fields[5] + " - " + fields[6]);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read course list.");
            e.printStackTrace();
        }
    }

    private boolean isValidStudent(String studentId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("student.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(studentId + ",")) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isValidCourse(String courseCode) {
        try (BufferedReader reader = new BufferedReader(new FileReader("course.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(courseCode + ",")) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void printCourseDetails(String courseCode) {

        try (BufferedReader reader = new BufferedReader(new FileReader("course.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(courseCode + ",")) {
                    //System.out.println("- " + line);
                    String[] fields = line.split(",");
                    if (fields.length >= 7) {
                        System.out.println("- " + fields[0] + "  -  " + fields[1] + " - " + fields[2] + " - " + fields[3] + " - " + fields[4] + " - " + fields[5] + " - " + fields[6]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
