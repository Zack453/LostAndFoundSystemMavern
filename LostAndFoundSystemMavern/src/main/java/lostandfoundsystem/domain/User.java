package lostandfoundsystem.domain;

// 230939023

/*
 * This is the User Class.
 *
 * It acts as the Parent Class for:
 * Student, Lecturer, Admin and Staff.
 *
 * Student, Lecturer, Admin and Staff
 * extend the User class.
 */

public class User {

    private int personId;
    private String name;
    private String surname;
    private String password;
    private String secQuestion;
    private String secAnswer;
    private String role; 

    public User() {
    }

    public User(
            int personId,
            String name,
            String surname,
            String password,
            String secQuestion,
            String secAnswer,
            String role
    ) {
        this.personId = personId;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.secQuestion = secQuestion;
        this.secAnswer = secAnswer;
        this.role = role;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecQuestion() {
        return secQuestion;
    }

    public void setSecQuestion(String secQuestion) {
        this.secQuestion = secQuestion;
    }

    public String getSecAnswer() {
        return secAnswer;
    }

    public void setSecAnswer(String secAnswer) {
        this.secAnswer = secAnswer;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "User{"
                + "personId=" + personId
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + '}';
    }
}
