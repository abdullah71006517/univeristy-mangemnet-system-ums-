import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        course course = new course();
        Student   student = new  Student();
        AcademicRecordManager   academicRecordManager = new  AcademicRecordManager();
        Scanner  scanner =  new  Scanner(System.in);

        if ( login() ) {
            while (true) {
                System.out.println(" Main menu:");
                System.out.println("1. Student Management");
                System.out.println("2. Course Management");
                System.out.println("3. CGPA Management");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 ->  studentMenu(student, scanner);
                    case 2 ->  courseMenu(course, scanner);
                    case 3 ->  gpaMenu(academicRecordManager, scanner);
                    case 4 -> {
                        System.out.println("👋 Exiting system. Goodbye!");
                        return;
                    }
                    default -> System.err.println(" Invalid option. Try again.");
                }
            }
        } else {
            System.err.println(" Too many failed login attempts. Program exiting.");
        }
    }

    private static boolean login() {
        Scanner scanner = new Scanner(System.in);
        int failedAttempts = 0;

        while (failedAttempts < 5) {
            System.out.println("\n Authority login ===");
            System.out.print("ID/username: ");
            String id = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (password.equals("admin")) {
                System.out.println(" Login successful!");
                return true;
            } else {
                failedAttempts++;
                System.err.println(" Invalid credentials. Attempts left: " + (5 - failedAttempts));
            }
        }

        return false;
    }

    private static void studentMenu(Student student, Scanner scanner) {
        while (true) {
            System.out.println("\n  Student menu ");
            System.out.println("1. Add Student");
            System.out.println("2. View Student");
            System.out.println("3. Update Student");
            System.out.println("4. Reset Password");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                 case 1 -> student.addStudent();
                case 2 -> student.viewStudentById();

                  case 3 -> student.updateStudent();

                case 4 -> student.resetPassword();
                  case 5 -> {
                    return;
                }
                default -> System.err.println("Invalid option.");
            }
        }
    }

    private static void courseMenu(course course, Scanner scanner) {
        while (true) {
            System.out.println(" ==Course meno== ");
            System.out.println("1. Add Course");
            System.out.println("2. Assign Course to Student");
            System.out.println("3. View Courses by Student ID");
            System.out.println("4. View All Courses");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> course.addCourse();
                case 2 -> course.assignCourseToStudent();
                case 3 -> course.viewCoursesByStudent();
                case 4 -> course.viewAllCourses();
                case 5 -> {
                    return;
                }
                default -> System.err.println("Invalid option.");
            }
        }
    }

    private static void gpaMenu(AcademicRecordManager manager, Scanner scanner) {
        while (true) {
            System.out.println(" CGPA MENU:");
            System.out.println("1. Submit Result");
            System.out.println("2. View All GPA");
            System.out.println("3. Calculate CGPA");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter Semester (e.g., summer2025): ");
                    String semester = scanner.nextLine();

                    System.out.print("Enter GPA: ");
                    double gpa = scanner.nextDouble();
                    scanner.nextLine();
                    manager.submitSemesterGPA(studentId, semester, gpa);
                }
                case 2 -> {
                      System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();

                      manager.viewAllGPA(studentId);
                }
                case 3 -> {
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();

                    manager.calculateCGPA(studentId);
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }
}
