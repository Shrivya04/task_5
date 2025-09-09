import java.io.*;
import java.util.*;

// Student class
class Student {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }

    public void setName(String name) { this.name = name; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll No: " + rollNumber + ", Grade: " + grade;
    }
}

// Student Management System
class StudentManagementSystem {
    private List<Student> students;
    private final String FILE_NAME = "students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadFromFile();
    }

    // Add student
    public void addStudent(Student student) {
        students.add(student);
        saveToFile();
    }

    // Remove student by roll number
    public boolean removeStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equals(rollNumber)) {
                students.remove(s);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // Search student by roll number
    public Student searchStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equals(rollNumber)) {
                return s;
            }
        }
        return null;
    }

    // Display all students
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    // Save to file
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.println(s.getName() + "," + s.getRollNumber() + "," + s.getGrade());
            }
        } catch (IOException e) {
            System.out.println("Error saving students to file.");
        }
    }

    // Load from file
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length == 3) {
                    students.add(new Student(data[0], data[1], data[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading students from file.");
        }
    }
}

// Main class with user interface
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter roll number: ");
                    String roll = scanner.nextLine().trim();
                    System.out.print("Enter grade: ");
                    String grade = scanner.nextLine().trim();

                    if (name.isEmpty() || roll.isEmpty() || grade.isEmpty()) {
                        System.out.println("Error: All fields are required!");
                    } else {
                        sms.addStudent(new Student(name, roll, grade));
                        System.out.println("Student added successfully.");
                    }
                    break;

                case "2":
                    System.out.print("Enter roll number to remove: ");
                    String rollToRemove = scanner.nextLine().trim();
                    if (sms.removeStudent(rollToRemove)) {
                        System.out.println("Student removed successfully.");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case "3":
                    System.out.print("Enter roll number to search: ");
                    String rollToSearch = scanner.nextLine().trim();
                    Student s = sms.searchStudent(rollToSearch);
                    if (s != null) {
                        System.out.println("Student found: " + s);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case "4":
                    sms.displayAllStudents();
                    break;

                case "5":
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

