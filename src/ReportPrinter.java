public class ReportPrinter {
    private GradeConstants  C;
    private GradeCalculator calculator;

    public ReportPrinter(GradeConstants c, GradeCalculator calculator) {
        this.C          = c;
        this.calculator = calculator;
    }

    public void printSeparatorLine() {
        System.out.println("===============================================================");
    }

    // [REPORT] GRADE REPORT CALCULATOR
    // prints the grades
    public void printReport(StudentRepository repo) {
        if (repo.getCount() == 0) {
            System.out.println("   No records available   ");
            return;
        }
        printSeparatorLine();
        System.out.println("   No.  Name               Raw Grade   Grade   Rank    Remarks ");
        System.out.println("---------------------------------------------------------------");
        printSeparatorLine();

        // loops through each student and prints their information
        for (int i = 0; i < repo.getCount(); i++) {
            Student s = repo.getStudent(i);
            System.out.println(String.format("   %-4s %-18s %-11s %-7s %-7s %-7s",
                    (i + 1),
                    s.getName(),
                    s.getRawGrade(),
                    s.getNumericGrade(),
                    s.getLetterRank(),
                    calculator.getRemarks(s.getLetterRank())));
        }
        printSeparatorLine();
    }

    // [STATS] CLASS STATISTICS METHOD
    // computes the lowest, highest, and average grade
    public void printClassStats(StudentRepository repo) {
        if (repo.getCount() == 0) {
            System.out.println("   No records available   ");
            return;
        }

        // sorting variable setup
        double sum = 0;
        double highest = repo.getStudent(0).getRawGrade();
        double lowest  = repo.getStudent(0).getRawGrade();
        int highestIndex = 0;
        int lowestIndex  = 0;

        // sorting algo
        for (int i = 0; i < repo.getCount(); i++) {
            double g = repo.getStudent(i).getRawGrade();
            sum += g;
            if (g > highest) { highest = g; highestIndex = i; }
            if (g < lowest)  { lowest  = g; lowestIndex  = i; }
        }

        // table printing
        printSeparatorLine();
        System.out.println("                   CLASS STATISTICS REPORT                    ");
        printSeparatorLine();
        System.out.printf("   %-22s %d%n", "Total Students:", repo.getCount());
        System.out.println("---------------------------------------------------------------");
        String highestInfo = String.format("%s (%.2f) - Rank: %s-tier",
                repo.getStudent(highestIndex).getName(), highest, calculator.assignLetterRank(highest));
        String lowestInfo = String.format("%s (%.2f) - Rank: %s-tier",
                repo.getStudent(lowestIndex).getName(), lowest, calculator.assignLetterRank(lowest));
        String meanInfo = String.format("%.2f - Rank: %s-tier",
                (sum / repo.getCount()), calculator.assignLetterRank(sum / repo.getCount()));
        System.out.printf("   %-22s %s%n", "Highest Raw Grade:", highestInfo);
        System.out.printf("   %-22s %s%n", "Lowest Raw Grade:", lowestInfo);
        System.out.printf("   %-22s %s%n", "Class Mean:", meanInfo);
        printSeparatorLine();
    }
}