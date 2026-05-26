import java.util.Scanner;

public class IDVerifier {
    private GradeConstants C;
    private Scanner sc = new Scanner(System.in);

    public IDVerifier(GradeConstants c) { this.C = c; }

    // loops until user exits
    public void verifyID() {
        while (true) {
            System.out.println("Enter ID: ");
            String id = sc.nextLine().trim();
            String result = validateID(id);
            System.out.println(result);
            System.out.println("Check another ID? (yes/no to continue): ");
            String again = sc.nextLine().trim().toLowerCase();
            if (!again.equals("y") && !again.equals("yes")) break;
        }
    }

    // returns a verdict string
    String validateID(String id) {
        if (id == null || id.length() != C.ID_LENGTH || !id.matches("\\d+")) {
            return "Invalid ID number. ID must be exactly " + C.ID_LENGTH + " digits.";
        }
        int dot = calculateDotProduct(id);
        if (dot % C.ID_DIVISOR == 0) {
            if (dot / C.ID_DIVISOR >= C.FACULTY_THRESHOLD) {
                return "Valid faculty ID number.";
            } else {
                return "Valid student ID number.";
            }
        } else {
            return "Invalid ID number. Dot product must be divisible by " + C.ID_DIVISOR + ".";
        }
    }

    // calculates the dot product
    int calculateDotProduct(String id) {
        int dot = 0;
        for (int i = 0; i < id.length(); i++) {
            dot += Character.getNumericValue(id.charAt(i)) * (i + 1);
        }
        return dot;
    }
}