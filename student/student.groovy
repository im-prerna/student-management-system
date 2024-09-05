

import java.io.*;
import java.util.*;

    class Student implements Serializable {
        private static final long serialVersionUID = 1L;
        int id;
        String name;
        int age;
        String grade;

        public Student(int id, String name, int age, String grade) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Grade: " + grade;
        }
    }

    public class student{
        private static final String FILE_NAME = "students.dat";
        private List<Student> students;

        public student() {
            students = new ArrayList<>();
            loadStudentsFromFile();
        }

        // Add a new student
        public void addStudent(Student student) {
            students.add(student);
            saveStudentsToFile();
            System.out.println("Student added successfully.");
        }

        // View all students
        public void viewStudents() {
            if (students.isEmpty()) {
                System.out.println("No students available.");
            } else {
                for (Student student : students) {
                    System.out.println(student);
                }
            }
        }

        // Update a student
        public void updateStudent(int id, String name, int age, String grade) {
            boolean found = false;
            for (Student student : students) {
                if (student.id == id) {
                    student.name = name;
                    student.age = age;
                    student.grade = grade;
                    found = true;
                    saveStudentsToFile();
                    System.out.println("Student updated successfully.");
                    break;
                }
            }
            if (!found) {
                System.out.println("Student with ID " + id + " not found.");
            }
        }

        // Delete a student
        public void deleteStudent(int id) {
            boolean found = false;
            Iterator<Student> iterator = students.iterator();
            while (iterator.hasNext()) {
                Student student = iterator.next();
                if (student.id == id) {
                    iterator.remove();
                    found = true;
                    saveStudentsToFile();
                    System.out.println("Student deleted successfully.");
                    break;
                }
            }
            if (!found) {
                System.out.println("Student with ID " + id + " not found.");
            }
        }

        // Save students to a file
        private void saveStudentsToFile() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                oos.writeObject(students);
            } catch (IOException e) {
                System.out.println("Error saving students: " + e.getMessage());
            }
        }

        // Load students from a file
        private void loadStudentsFromFile() {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    students = (List<Student>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error loading students: " + e.getMessage());
                }
            }
        }

        // Main menu
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            student sms = new student();

            while (true) {
                System.out.println("\n--- Student Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter Grade: ");
                        String grade = sc.nextLine();
                        Student newStudent = new Student(id, name, age, grade);
                        sms.addStudent(newStudent);
                        break;

                    case 2:
                        sms.viewStudents();
                        break;

                    case 3:
                        System.out.print("Enter Student ID to Update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter New Age: ");
                        int newAge = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter New Grade: ");
                        String newGrade = sc.nextLine();
                        sms.updateStudent(updateId, newName, newAge, newGrade);
                        break;

                    case 4:
                        System.out.print("Enter Student ID to Delete: ");
                        int deleteId = sc.nextInt();
                        sms.deleteStudent(deleteId);
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid option! Please try again.");
                }
            }
        }
    }


