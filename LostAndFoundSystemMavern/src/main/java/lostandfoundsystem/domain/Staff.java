package lostandfoundsystem.domain;

// 230939023

public class Staff extends User {

    private String employeeId;
    private String department;

    public Staff() {
        super();
    }

    public Staff(
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
                role
        );

        this.employeeId = employeeId;
        this.department = department;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Staff{"
                + "personId=" + getPersonId()
                + ", name='" + getName() + '\''
                + ", surname='" + getSurname() + '\''
                + ", employeeId='" + employeeId + '\''
                + ", department='" + department + '\''
                + '}';
    }
}
