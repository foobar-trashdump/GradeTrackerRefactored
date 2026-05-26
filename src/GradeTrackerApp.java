import java.util.Scanner;

public class GradeTrackerApp {
    private IDVerifier          idVerifier;
    private StudentInputHandler inputHandler;
    private ReportPrinter       printer;
    private StudentRepository   repo;
    private GradeConstants      C;
    private Scanner sc = new Scanner(System.in);

    public GradeTrackerApp() {
        C            = new GradeConstants();
        GradeCalculator calc = new GradeCalculator(C);
        repo         = new StudentRepository(C);
        inputHandler = new StudentInputHandler(calc, C);
        printer      = new ReportPrinter(C, calc);
        idVerifier   = new IDVerifier(C);
    }

    // [MAIN] MAIN METHOD
    public static void main(String[] args) {
        GradeTrackerApp gt = new GradeTrackerApp();
        gt.displayMenu();
    }

    // [MENU] DISPLAY MENU METHOD
    // prints the main menu and loops until the user chooses to exit
    public void displayMenu() {
        // menu printing
        while (true) {
            System.out.println("===============================================================");
            System.out.println("                      GRADE TRACKER MENU                       ");
            System.out.println("===============================================================");
            System.out.println("   1. Enter Student Data");
            System.out.println("   2. View Report");
            System.out.println("   3. View Class Statistics");
            System.out.println("   4. Verify ID Number");
            System.out.println("   5. Exit");
            System.out.println("===============================================================");

            System.out.println("   Enter Choice (1-5): ");
            String choice = sc.nextLine();

            // switch function to execute instructions
            switch (choice) {
                // case 1 goes to inputStudentData(), name is self-explanatory
                case "1":
                    inputStudentData();
                    break;
                /* case 2 prints out the report cards of all the students previously
                entered, returns nothing if there were no students entered */
                case "2":
                    printer.printReport(repo);
                    break;
                /* case 3 returns the highest and lowest average grade
                as well as the mean grade of the students entered */
                case "3":
                    printer.printClassStats(repo);
                    break;
                /* case 4 asks for an ID and verifies its validity */
                case "4":
                    idVerifier.verifyID();
                    break;
                case "5":
                    // ends the program
                    System.out.println("   Closing Program");
                    return;
                default:
                    System.out.println("   Invalid Choice, Choose an option from 1-5");
                    break;
            }
        }
    }

    // [INPUT] STUDENT DATA INPUT METHOD
    // Reads the inputted grades
    public void inputStudentData() {
        // checks if the student count does not exceed the maximum
        if (repo.getCount() >= C.MAX_STUDENTS) {
            System.out.println("ERROR: Student Limit Reached");
            return;
        }
        // name entry
        System.out.println("\n========= Entering Data for Student #" + (repo.getCount() + 1) + " =========");

        Student s = inputHandler.inputOneStudent(repo.getCount() + 1);
        repo.addStudent(s);

        System.out.println("Data Successfully Saved!");
    }
}