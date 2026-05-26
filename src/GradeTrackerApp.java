import java.util.Scanner;

// [CLASS] GRADE TRACKER APP
// main entry point; wires all components together and drives the menu loop
// single reason to change: menu options or component wiring changes
public class GradeTrackerApp {
    // [FIELD] component references — each handles one responsibility
    private IDVerifier          idVerifier;
    private StudentInputHandler inputHandler;
    private ReportPrinter       printer;
    private StudentRepository   repo;
    private GradeConstants      C;

    // [FIELD] shared scanner for reading menu choices
    private Scanner sc = new Scanner(System.in);

    // [CONSTRUCTOR] instantiates and wires all components
    public GradeTrackerApp() {
        C            = new GradeConstants();           // [TRACE] constants created first, passed to everything else
        GradeCalculator calc = new GradeCalculator(C); // [TRACE] calculator depends on constants
        repo         = new StudentRepository(C);        // [TRACE] repository depends on constants
        inputHandler = new StudentInputHandler(calc, C, idVerifier);// [TRACE] input handler depends on calculator + constants + verifier
        printer      = new ReportPrinter(C, calc);      // [TRACE] printer depends on calculator + constants
        idVerifier   = new IDVerifier(C);               // [TRACE] verifier depends on constants
    }

    // [MAIN] MAIN METHOD
    public static void main(String[] args) {
        GradeTrackerApp gt = new GradeTrackerApp(); // [TRACE] create app instance on heap
        gt.displayMenu();                            // [TRACE] hand control to the menu loop
    }

    // [MENU] DISPLAY MENU METHOD
    // prints the main menu and loops until the user chooses to exit
    public void displayMenu() {
        // [TRACE] menu loop runs indefinitely until return is hit on exit case
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
            // [TRACE] prompt for choosing which option to execute
            System.out.print("   Enter Choice (1-5): ");
            String choice = sc.nextLine();

            // [TRACE] switch function to execute instructions
            // used a switch function because I hate how messy if/else ladders look
            switch (choice) {
                // [DECISION] case 1 goes to inputStudentData(), name is self-explanatory
                case "1":
                    inputStudentData();
                    break;
                /* [DECISION] case 2 goes to printReport(), this method prints out the report cards of all the students previously
                entered, returns nothing if there were no students entered */
                case "2":
                    printer.printReport(repo);
                    break;
                /* [DECISION] case 3 goes to printClassStats(), it gives the highest and lowest average grade
                as well as the mean grade of the students entered */
                case "3":
                    printer.printClassStats(repo);
                    break;
                // [DECISION] case 4 goes to verifyID(), prompts for an ID and checks its validity
                case "4":
                    idVerifier.verifyID();
                    break;
                case "5":
                    // [DECISION] ends the program
                    System.out.println("   Closing Program");
                    return;
                default:
                    // [DECISION] any input outside 1-5 is rejected
                    System.out.println("   Invalid Choice, Choose an option from 1-5");
                    break;
            }
        }
    }

    // [INPUT] STUDENT DATA INPUT METHOD
    // reads the inputted grades for one student and saves them to the repository
    public void inputStudentData() {
        // [DECISION] check if the student count does not exceed the maximum before proceeding
        if (repo.getCount() >= C.MAX_STUDENTS) {
            System.out.println("ERROR: Student Limit Reached");
            return;
        }

        // [TRACE] print entry header showing which student number is being entered
        System.out.println("\n========= Entering Data for Student #" + (repo.getCount() + 1) + " =========");

        // [TRACE] delegate full data collection to inputHandler, receive completed Student object
        Student s = inputHandler.inputOneStudent(repo.getCount() + 1);

        // [TRACE] store the completed student in the repository
        repo.addStudent(s);

        System.out.println("Data Successfully Saved!");
    }
}