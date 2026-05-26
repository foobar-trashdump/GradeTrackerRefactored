public class GradeCalculator {
    private GradeConstants C;

    public GradeCalculator(GradeConstants c) { this.C = c; }

    // [AVERAGE] AVERAGE CALCULATION METHOD
    double computeAverage(double[] scores) {
        if (scores == null || scores.length == 0) return 0.0;
        double sum = 0;
        for (double s : scores) {
            sum += s;
        }
        return sum / scores.length;
    }

    // [COMPUTE] RAW GRADE CALCULATION METHOD
    // calculates the raw grade, weighted scores applied
    double computeRawGrade(double labPerformance, double classParticipation,
                           double teacherEvaluation, double practicalExam, double projectGrade) {
        return (labPerformance    * C.LAB_WEIGHT)           +
                (classParticipation * C.PARTICIPATION_WEIGHT) +
                (teacherEvaluation  * C.TEACHER_WEIGHT)       +
                (practicalExam      * C.EXAM_WEIGHT)          +
                (projectGrade       * C.PROJECT_WEIGHT);
    }

    // [GRADE] NUMERIC GRADE ASSIGNMENT METHOD
    // uses a series of if statements to assign the numeric grade
    String assignNumericGrade(double avg) {
        if (avg >= 96) return "4.0";
        if (avg >= 92) return "3.5";
        if (avg >= 88) return "3.0";
        if (avg >= 83) return "2.5";
        if (avg >= 78) return "2.0";
        if (avg >= 74) return "1.5";
        if (avg >= 70) return "1.0";
        return "0.0";
    }

    // [GRADE] LETTER GRADE ASSIGNMENT METHOD
    // uses a series of if statements to assign the letter grade
    char assignLetterRank(double avg) {
        if (avg >= 96) return 'S';
        if (avg >= 92) return 'A';
        if (avg >= 88) return 'B';
        if (avg >= 83) return 'C';
        if (avg >= 78) return 'D';
        if (avg >= 74) return 'E';
        if (avg >= 70) return 'P';
        return 'F';
    }

    // [GRADE] REMARK ASSIGNMENT METHOD
    // uses a switch case to assign the grade remarks
    String getRemarks(char grade) {
        return getRemarks(String.valueOf(grade).toUpperCase());
    }
    String getRemarks(String grade) {
        switch (grade.trim().toUpperCase()) {
            case "S":
            case "4.0": return "Excellent";
            case "A":
            case "3.5": return "Superior";
            case "B":
            case "3.0": return "Very Good";
            case "C":
            case "2.5": return "Good";
            case "D":
            case "2.0": return "Satisfactory";
            case "E":
            case "1.5": return "Fair";
            case "P":
            case "1.0": return "Poor/Passed";
            case "F":
            case "0.0": return "Failed";
            default:    return "Unknown";
        }
    }
}