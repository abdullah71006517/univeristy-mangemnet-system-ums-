# 🎓 Java Console-Based Student Management System

This is a console-based Java application that helps manage student and course information in a university or academic environment. It supports secure student management, course assignments, GPA submission, CGPA calculation, and file-based data storage.

---

##  Project Structure

- `Student.java` – Manages student records, password reset, updates, and GPA.
- `Course.java` – Handles course creation and assignment.
- `AcademicRecordManager.java` – Manages semester GPA and CGPA per student.
- `Main.java` – Entry point with menu-based navigation.
- `student.txt` – Stores student information.
- `course.txt` – Stores course information.
- `student_courses.txt` – Maps student IDs to assigned courses.
- `results.txt` – Stores semester GPA for each student.

---

##  Features

### 👤 Student Management
- Add new students
- View student details by ID
- Update student information (name, program, batch)
- Secure login system with password masking and attempt limits
- Password reset feature
- Delete student records
- Count total number of students

### 📚 Course Management
- Add new courses
- Assign courses to students
- View courses assigned to a student
- View all available courses

### 📊 Academic Records
- Submit semester GPA for students
- View all GPA records by student ID
- Calculate CGPA per student
- Store semester-wise results in `results.txt`

---

## 🔐 Security
- Password-protected login
- Password masking using `Console.readPassword()`
- Maximum login attempt limit
- Option to reset passwords

---

## 💾 File Storage
All data is stored using `RandomAccessFile`, `BufferedReader`, and `BufferedWriter` in the following files:
- `student.txt`
- `course.txt`
- `student_courses.txt`
- `results.txt`

---

## 🚀 How to Run

1. Compile all `.java` files:
   ```bash
   javac *.java
