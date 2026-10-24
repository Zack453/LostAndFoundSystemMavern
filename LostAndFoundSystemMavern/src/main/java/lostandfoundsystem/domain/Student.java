package lostandfoundsystem.domain;

// 230939023

public class Student extends User {

    private String studentNumber;

    public Student() {
        super();
    }

    public Student(
            int personId,
            String name,
            String surname,
            String password,
            String secQuestion,
            String secAnswer,
            String studentNumber,
            String role
    ) {
        super(
                personId,
                name,
                surname,
                password,
                secQuestion,
                secAnswer,
                role
        );

        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String toString() {
        return "Student{"
                + "personId=" + getPersonId()
                + ", name='" + getName() + '\''
                + ", surname='" + getSurname() + '\''
                + ", studentNumber='" + studentNumber + '\''
                + '}';
    }
}
