public class Student {
    private String name;
    private double rawGrade;
    private char   letterRank;
    private String idNumber;
    private String numericGrade;

    public String getName()         { return name; }
    public double getRawGrade()     { return rawGrade; }
    public char   getLetterRank()   { return letterRank; }
    public String getIdNumber()     { return idNumber; }
    public String getNumericGrade() { return numericGrade; }

    public void setName(String name)                { this.name = name; }
    public void setRawGrade(double rawGrade)         { this.rawGrade = rawGrade; }
    public void setLetterRank(char letterRank)       { this.letterRank = letterRank; }
    public void setIdNumber(String idNumber)         { this.idNumber = idNumber; }
    public void setNumericGrade(String numericGrade) { this.numericGrade = numericGrade; }
}