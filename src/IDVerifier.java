import java.util.Scanner;

// [CLASS] ID VERIFIER
// handles ID number validation using the dot-product divisibility method
// single reason to change: ID format or verification algorithm changes
public class IDVerifier {
    // [FIELD] reference to shared constants for ID rules
    private GradeConstants C;

    // [FIELD] shared scanner for reading console input
    private Scanner sc = new Scanner(System.in);

    // [CONSTRUCTOR] receives constants so ID rules are not hardcoded here
    public IDVerifier(GradeConstants c) { this.C = c; }

    // [VERIFY] ID VERIFICATION LOOP
    // repeatedly prompts the user to enter an ID and prints the result
    // loops until the user declines to check another
    public void verifyID() {
        System.out.println("\n===============================================================");
        System.out.println("                       ID VERIFICATION                        ");
        System.out.println("===============================================================");

        while (true) { // [TRACE] keep looping until user says no
            System.out.print("Enter ID: ");
            String id = sc.nextLine().trim();      // [TRACE] read and clean the input
            String result = validateID(id);         // [TRACE] validate and get verdict

            // [DECISION] print message based on verdict returned by validateID
            switch (result) {
                case "Student":
                    System.out.println("Valid Student ID number.");
                    break;
                case "Faculty":
                    System.out.println("Valid faculty ID number.");
                    break;
                default:
                    // [DECISION] anything other than Student/Faculty means invalid
                    System.out.println("Invalid ID number. Dot product must be divisible by 11.");
                    break;
            }

            System.out.print("Check another ID? (yes/y to continue): ");
            String again = sc.nextLine().trim().toLowerCase(); // [TRACE] normalize response
            // [DECISION] exit loop if user did not answer "y" or "yes"
            if (!again.equals("yes") && !again.equals("y")) break;
        }

        System.out.println("===============================================================");
    }

    // [ID] PROMPT AND VALIDATE ID DURING STUDENT DATA ENTRY
    // loops until a valid Student ID is entered; rejects Faculty IDs and invalid inputs
    public String inputStudentID() {
        while (true) { // [TRACE] keep prompting until a valid student ID is given
            System.out.print("Enter Student ID (8 digits): ");
            String id = sc.nextLine().trim();
            String result = validateID(id); // [TRACE] validate the entered ID

            // [DECISION] accept only if validated as a Student ID
            if (result.equals("Student")) {
                System.out.println("   ID Verified: Valid Student ID");
                return id;
            } else if (result.equals("Faculty")) {
                // [DECISION] reject faculty IDs — must be a student ID
                System.out.println("   ERROR: That is a Faculty ID. Please enter a Student ID.");
            } else {
                // [DECISION] reject malformed or invalid IDs
                System.out.println("   ERROR: Invalid ID. Must be 8 digits and pass validation.");
            }
        }
    }

    // [VALIDATE] ID VALIDATION METHOD
    // returns "Student", "Faculty", or "Invalid" based on dot-product calculation
    String validateID(String id) {
        // [DECISION] reject if wrong length or contains non-digit characters
        if (id == null || id.length() != C.ID_LENGTH || !id.matches("\\d+")) {
            return "Invalid";
        }

        // [TRACE] compute the dot product of the ID digits
        int dot = calculateDotProduct(id);

        // [DECISION] check divisibility by 11
        if (dot % C.ID_DIVISOR != 0) return "Invalid"; // [DECISION] not divisible — invalid

        // [DECISION] distinguish student vs faculty by the quotient
        if (dot / C.ID_DIVISOR >= C.FACULTY_THRESHOLD) {
            return "Faculty";
        } else {
            return "Student";
        }
    }

    // [COMPUTE] DOT PRODUCT CALCULATION METHOD
    // multiplies each digit by a descending weight (8 down to 1) and sums the results
    // example: "11191031" → (1*8)+(1*7)+(1*6)+(9*5)+(1*4)+(0*3)+(3*2)+(1*1) = 77
    int calculateDotProduct(String id) {
        int dot = 0;
        // [TRACE] iterate over each character in the ID string
        for (int i = 0; i < id.length(); i++) {
            dot += Character.getNumericValue(id.charAt(i)) * (id.length() - i); // [TRACE] digit * descending weight
        }
        return dot; // [TRACE] return final dot product
    }
}