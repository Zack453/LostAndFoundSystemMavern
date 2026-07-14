package classes;

/*

230939023
This is the Person Class
It will act a Parent Class for Student, Lecturer Admin, and Staff

Student, Lecturer, Admin and Staff Should be an extended class of Person
*/


public class Person {

    private int personId;
    private String name;
    private String surname;
    private String password;
    private String secQuestion;
    private String secAnswer;
    
    public Person() {
    }

    public Person(int personId, String name, String surname, String password, String secQuestion, String secAnswer) {
        this.personId = personId;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.secQuestion = secQuestion;
        this.secAnswer = secAnswer;
    }

    public int getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getSecQuestion() {
        return secQuestion;
    }

    public String getSecAnswer() {
        return secAnswer;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecQuestion(String secQuestion) {
        this.secQuestion = secQuestion;
    }

    public void setSecAnswer(String secAnswer) {
        this.secAnswer = secAnswer;
    }
    
}
