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
        System.out.println("   Total Students:  " + repo.getCount());
        System.out.println("---------------------------------------------------------------");
        System.out.println("   Highest Raw Grade:   " + repo.getStudent(highestIndex).getName() +
                " (" + String.format("%.2f", highest) + ") - Rank: " + calculator.assignLetterRank(highest) + "-tier");
        System.out.println("   Lowest Raw Grade:    " + repo.getStudent(lowestIndex).getName() +
                " (" + String.format("%.2f", lowest) + ") - Rank: " + calculator.assignLetterRank(lowest) + "-tier");
        System.out.println("   Class Mean:          " + String.format("%.2f", sum / repo.getCount()) +
                " - Rank: " + calculator.assignLetterRank(sum / repo.getCount()) + "-tier");
        printSeparatorLine();
    }
}