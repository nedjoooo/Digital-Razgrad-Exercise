import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Main {
    public static String[] eightA;
    public static int eightAStudents = 0;
    public static String[] eightB;
    public static int eightBStudents = 0;
    public static String[] nineA;
    public static int nineAStudents = 0;
    public static String[] nineB;
    public static int nineBStudents = 0;

    public static void main(String[] args) {
        try {
            distributeStudents();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        printStudents();
        generateGrades();
    }

    public static void generateGrades() {
        generateStudentGrades(eightA, eightAStudents, "8A");
        generateStudentGrades(eightB, eightBStudents, "8B");
        generateStudentGrades(nineA, nineAStudents, "9A");
        generateStudentGrades(nineB, nineBStudents, "9B");
//        try (BufferedWriter bf = new BufferedWriter(new FileWriter("students-list.csv"))) {
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void generateStudentGrades(String[] students, int studentsCount, String className) {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter("students-list.csv", true))) {
            for (int i = 0; i < studentsCount; i++) {
                String[] student = students[i].split(" ");
                bf.write(student[0] + ";");
                bf.write(student[1] + ";");
                bf.write(className + ";");
                double averageOfGrades = 0;
                if (isMale(student[1])) {
                    int countOfGrades = randomNumber(6, 15);
                    for (int j = 0; j < countOfGrades; j++) {
                        int grade = randomNumber(2, 6);
                        bf.write(grade + ";");
                        averageOfGrades += grade;
                    }
                    averageOfGrades /= countOfGrades;
                    bf.write(averageOfGrades + ";");
                } else {
                    int countOfGrades = randomNumber(6, 15);
                    for (int j = 0; j < countOfGrades; j++) {
                        int grade = randomNumber(4, 6);
                        bf.write(grade + ";");
                        averageOfGrades += grade;
                    }
                    averageOfGrades /= countOfGrades;
                    bf.write(averageOfGrades + ";");
                }
                bf.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean isMale(String surname) {
        if(surname.charAt(surname.length() - 1) == 'а') {
            return false;
        }

        return true;
    }

    public static int randomNumber(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static void distributeStudents() throws FileNotFoundException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("students.txt"))){
            String line = "";
            int countStudents = (int) Files.lines(Paths.get("students.txt")).count();
            initializeClasses(countStudents);
            while ((line = bufferedReader.readLine()) != null) {
                String[] student = line.split(" ");
                distributeToClass(student);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printStudents() {
        System.out.println("8A");
        for (int i = 0; i < eightAStudents; i++) {
            System.out.println(eightA[i]);
        }
        System.out.println("8B");
        for (int i = 0; i < eightBStudents; i++) {
            System.out.println(eightB[i]);
        }
        System.out.println("9A");
        for (int i = 0; i < nineAStudents; i++) {
            System.out.println(nineA[i]);
        }
        System.out.println("9B");
        for (int i = 0; i < nineBStudents; i++) {
            System.out.println(nineB[i]);
        }
    }

    public static void initializeClasses(int countStudents) {
        eightA = new String[countStudents];
        eightB = new String[countStudents];
        nineA = new String[countStudents];
        nineB = new String[countStudents];
    }

    public static void distributeToClass(String[] student) {
        String nameStudent = student[0] + student[1];
        if(Integer.parseInt(student[2]) < 15) {
            if(nameStudent.length() < 15) {
                eightA[eightAStudents++] = student[0] + " " + student[1] + " " + student[2];
            } else {
                eightB[eightBStudents++] = student[0] + " " + student[1] + " " + student[2];
            }
        } else {
            if(nameStudent.length() < 15) {
                nineA[nineAStudents++] = student[0] + " " + student[1] + " " + student[2];
            } else {
                nineB[nineBStudents++] = student[0] + " " + student[1] + " " + student[2];
            }
        }
    }
}