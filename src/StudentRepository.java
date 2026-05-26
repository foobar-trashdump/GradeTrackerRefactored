public class StudentRepository {
    private GradeConstants C;
    private Student[] students;
    private int count;

    public StudentRepository(GradeConstants c) {
        this.C        = c;
        this.students = new Student[c.MAX_STUDENTS];
        this.count    = 0;
    }

    public void addStudent(Student s) {
        if (count < C.MAX_STUDENTS) {
            students[count++] = s;
        }
    }

    public Student   getStudent(int index) { return students[index]; }
    public int       getCount()            { return count; }
    public Student[] getStudents()         { return students; }
    public void      setStudents(Student[] students) { this.students = students; }
    public void      setCount(int count)             { this.count = count; }
    public GradeConstants getC()                     { return C; }
    public void      setC(GradeConstants c)          { this.C = c; }
}