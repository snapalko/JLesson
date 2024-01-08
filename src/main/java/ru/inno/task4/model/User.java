package ru.inno.task4.model;

public class User {
    private Long id;
    private String userName;
    private String lastName;
    private String firstName;
    private String midlName;
    private String fio;
    private String accessDate;
    private String application;

    public User(){}
    public User(long id, String username, String fio) {
        this.id = id;
        this.userName = username;
        this.fio = fio;
    }

    public User(String fio, String accessDate) {
        this.fio = fio;
        this.accessDate = accessDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidlName() {
        return midlName;
    }

    public void setMidlName(String midlName) {
        this.midlName = midlName;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(String accessDate) {
        this.accessDate = accessDate;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", midlName='" + midlName + '\'' +
                ", fio='" + fio + '\'' +
//                ", accessDate='" + accessDate + '\'' +
//                ", application='" + application + '\'' +
                '}';
    }
}

