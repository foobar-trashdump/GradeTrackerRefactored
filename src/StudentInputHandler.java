import java.util.Scanner;

// [CLASS] STUDENT INPUT HANDLER
// handles all console input and validation for student data entry
// single reason to change: input prompts or validation rules change
public class StudentInputHandler {
    // [FIELD] reference to calculator for computing grades after input
    private GradeCalculator calculator;

    // [FIELD] reference to constants for validation bounds
    private GradeConstants  C;

    // [FIELD] reference to ID verifier for validating student IDs during entry
    private IDVerifier idVerifier;

    // [FIELD] shared scanner for reading console input
    private Scanner sc = new Scanner(System.in);

    // [CONSTRUCTOR] receives dependencies via constructor injection
    public StudentInputHandler(GradeCalculator calculator, GradeConstants c, IDVerifier idVerifier) {
        this.calculator = calculator;
        this.C          = c;
        this.idVerifier = idVerifier;
    }

    // [INPUT] LAB AVERAGE CALCULATION METHOD
    // prompts the user for 5 lab activity grades and returns their average
    public double inputLabPerformance() {
        int[] labGrades = new int[C.NUM_MODULES]; // [TRACE] array to hold each module score
        double sum = 0;

        // [TRACE] for loop asks for the grades of the 5 labs and appends them into an array
        for (int i = 0; i < C.NUM_MODULES; i++) {
            System.out.print("Enter Grade for Lab Activity " + (i + 1) + ": ");
            String input = sc.nextLine();
            labGrades[i] = Integer.parseInt(input.trim()); // [TRACE] parse and store score
        }

        // [TRACE] calculates the average grade of the labs
        for (int grade : labGrades) {
            sum += grade; // [TRACE] accumulate sum of all lab scores
        }

        return sum / labGrades.length; // [TRACE] divide total by number of modules
    }

    // [INPUT] COMPONENT GRADE INPUT METHOD
    // handles grade entry for other grade parameters
    // loops until a valid score within [MIN_SCORE, MAX_SCORE] is entered
    public double inputComponentScore(String prompt) {
        while (true) { // [TRACE] keep looping until valid input is received
            try {
                System.out.print(prompt);
                double score = Double.parseDouble(sc.nextLine().trim()); // [TRACE] attempt to parse input

                // [DECISION] accept score only if it falls within the valid range
                if (score >= C.MIN_SCORE && score <= C.MAX_SCORE) return score;

                System.out.println("   ERROR: Score must be between " + C.MIN_SCORE + " and " + C.MAX_SCORE);
            } catch (NumberFormatException e) {
                // [DECISION] catch non-numeric input and re-prompt
                System.out.println("   ERROR: Invalid Input ");
            }
        }
    }

    // [INPUT] NAME INPUT METHOD
    // reads and returns the student's name from the console
    public String inputName(int studentNumber) {
        System.out.print("Enter Student Name: ");
        return sc.nextLine(); // [TRACE] return raw input, no validation needed for names
    }

    // [INPUT] STUDENT COUNT INPUT METHOD
    // prompts for how many students will be entered this session
    // loops until an integer between 1 and MAX_STUDENTS is given
    public int inputStudentCount() {
        while (true) { // [TRACE] loop until valid count received
            try {
                System.out.println("How many students? (1-" + C.MAX_STUDENTS + "): ");
                int n = Integer.parseInt(sc.nextLine().trim()); // [TRACE] parse count input
                // [DECISION] reject counts outside valid range
                if (n >= 1 && n <= C.MAX_STUDENTS) return n;
                System.out.println("   ERROR: Enter a number between 1 and " + C.MAX_STUDENTS);
            } catch (NumberFormatException e) {
                // [DECISION] catch non-integer input and re-prompt
                System.out.println("   ERROR: Invalid input.");
            }
        }
    }

    // [INPUT] SINGLE STUDENT FULL DATA ENTRY METHOD
    // collects all grade components for one student, computes their final grade, and returns a Student object
    public Student inputOneStudent(int studentNumber) {
        Student s = new Student(); // [TRACE] create a new empty Student object on the heap

        // [TRACE] collect name first
        s.setName(inputName(studentNumber));

        // [TRACE] collect and validate student ID before proceeding to grades
        s.setIdNumber(idVerifier.inputStudentID());

        // [TRACE] collect each grade component individually
        double lab  = inputLabPerformance();
        double cp   = inputComponentScore("Enter Class Participation Grade: ");
        double te   = inputComponentScore("Enter Teacher's Evaluation Score: ");
        double pe   = inputComponentScore("Enter Practical Exam Grade: ");
        double proj = inputComponentScore("Enter Project Grade: ");

        // [TRACE] compute and assign all grade fields
        double raw = calculator.computeRawGrade(lab, cp, te, pe, proj);
        s.setRawGrade(raw);
        s.setNumericGrade(calculator.assignNumericGrade(raw));
        s.setLetterRank(calculator.assignLetterRank(raw));

        return s; // [TRACE] return fully populated Student object
    }
}