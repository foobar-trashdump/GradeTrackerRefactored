import java.util.Scanner;

public class StudentInputHandler {
    private GradeCalculator calculator;
    private GradeConstants  C;
    private Scanner sc = new Scanner(System.in);

    public StudentInputHandler(GradeCalculator calculator, GradeConstants c) {
        this.calculator = calculator;
        this.C          = c;
    }

    // [INPUT] LAB AVERAGE CALCULATION METHOD
    // prompts the user for 5 lab activity grades and returns their average
    public double inputLabPerformance() {
        int[] labGrades = new int[C.NUM_MODULES];
        double sum = 0;

        // for loop asks for the grades of the 5 labs and appends them into an array
        for (int i = 0; i < C.NUM_MODULES; i++) {
            System.out.println("Enter Grade for Lab Activity " + (i + 1) + ": ");
            String input = sc.nextLine();
            labGrades[i] = Integer.parseInt(input.trim());
        }

        // calculates the average grade of the labs
        for (int grade : labGrades) {
            sum += grade;
        }

        return sum / labGrades.length;
    }

    // [INPUT] COMPONENT GRADE INPUT METHOD
    // handles grade entry for other parameters
    public double inputComponentScore(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                double score = Double.parseDouble(sc.nextLine().trim());

                if (score >= C.MIN_SCORE && score <= C.MAX_SCORE) return score;

                System.out.println("   ERROR: Score must be between " + C.MIN_SCORE + " and " + C.MAX_SCORE);
            } catch (NumberFormatException e) {
                System.out.println("   ERROR: Invalid Input ");
            }
        }
    }

    public String inputName(int studentNumber) {
        System.out.println("Enter Student Name: ");
        return sc.nextLine();
    }

    public int inputStudentCount() {
        while (true) {
            try {
                System.out.println("How many students? (1-" + C.MAX_STUDENTS + "): ");
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= 1 && n <= C.MAX_STUDENTS) return n;
                System.out.println("   ERROR: Enter a number between 1 and " + C.MAX_STUDENTS);
            } catch (NumberFormatException e) {
                System.out.println("   ERROR: Invalid input.");
            }
        }
    }

    public Student inputOneStudent(int studentNumber) {
        Student s = new Student();

        s.setName(inputName(studentNumber));

        double lab = inputLabPerformance();
        double cp  = inputComponentScore("Enter Class Participation Grade: ");
        double te  = inputComponentScore("Enter Teacher's Evaluation Score: ");
        double pe  = inputComponentScore("Enter Practical Exam Grade: ");
        double proj = inputComponentScore("Enter Project Grade: ");

        double raw = calculator.computeRawGrade(lab, cp, te, pe, proj);
        s.setRawGrade(raw);
        s.setNumericGrade(calculator.assignNumericGrade(raw));
        s.setLetterRank(calculator.assignLetterRank(raw));

        return s;
    }
}