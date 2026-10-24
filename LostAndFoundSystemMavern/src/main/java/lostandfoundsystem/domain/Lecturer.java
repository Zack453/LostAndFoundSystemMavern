package lostandfoundsystem.domain;

// 230939023

public class Lecturer extends Staff {

    public Lecturer() {
        super();
    }

    public Lecturer(
            int personId,
            String name,
            String surname,
            String password,
            String secQuestion,
            String secAnswer,
            String role,
            String employeeId,
            String department
    ) {
        super(
                personId,
                name,
                surname,
                password,
                secQuestion,
                secAnswer,
                role,
                employeeId,
                department
        );
    }

    @Override
    public String toString() {
        return "Lecturer{"
                + "personId=" + getPersonId()
                + ", name='" + getName() + '\''
                + ", surname='" + getSurname() + '\''
                + ", employeeId='" + getEmployeeId() + '\''
                + ", department='" + getDepartment() + '\''
                + '}';
    }
}