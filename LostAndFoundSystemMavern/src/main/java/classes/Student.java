package classes;

/*
 * @230939023
 */

public class Student extends Person {
    
    private int studentId;
    private int studentNumber;
    private String studentEmail;
    private String course;
    private int year;
    
    public Student() {
    }

    public Student(int personId, String name, String surname, String password, String secQuestion, String secAnswer) {
        super(personId, name, surname, password, secQuestion, secAnswer);
        this.studentId = studentId;
        this.studentNumber = studentNumber;
        this.studentEmail = studentEmail;
        this.course = course;
        this.year = year;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public int getStudentNumber() {
        return studentNumber;
    }
    
    public String getStudentEmail() {
        return studentEmail;
    }
    
    public String getCourse() {
        return course;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setStudentId(int studentId){
        this.studentId = studentId;
    } 
    
    public void setStudentNumber(int studentNumber) {
         this.studentNumber = studentNumber;
    }
    
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    
    public void setCourse(String course){
        this.course = course;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
}


