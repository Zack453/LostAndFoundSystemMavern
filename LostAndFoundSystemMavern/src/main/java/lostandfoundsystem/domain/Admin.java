package lostandfoundsystem.domain;

// 230939023

public class Admin extends Staff {

    /*
     * Access Level:
     *
     * 0 = Can change personal posts
     * 1 = Can change all posts
     */

    private int accessLevel;

    public Admin() {
        super();
    }

    public Admin(
            int personId,
            String name,
            String surname,
            String password,
            String secQuestion,
            String secAnswer,
            String role,
            String employeeId,
            String department,
            int accessLevel
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

        this.accessLevel = accessLevel;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public String toString() {
        return "Admin{"
                + "personId=" + getPersonId()
                + ", name='" + getName() + '\''
                + ", surname='" + getSurname() + '\''
                + ", employeeId='" + getEmployeeId() + '\''
                + ", department='" + getDepartment() + '\''
                + ", accessLevel=" + accessLevel
                + '}';
    }
}