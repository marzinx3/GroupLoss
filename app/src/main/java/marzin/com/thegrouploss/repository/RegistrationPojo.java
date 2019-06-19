package marzin.com.thegrouploss.repository;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.Date;
import java.util.Objects;

@IgnoreExtraProperties
public class RegistrationPojo {
    private String email;
    private String username;
    private Date registrationDate;
    private String name;
    private String password;
    private String gender;

    public RegistrationPojo(String email, String username, String name, String password, String gender, Date registrationDate) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.registrationDate= registrationDate;
    }

    public RegistrationPojo(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationPojo)) return false;
        RegistrationPojo that = (RegistrationPojo) o;
        return Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getGender(), that.getGender())&&
                Objects.equals(getRegistrationDate(), that.getRegistrationDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEmail(), getUsername(), getName(), getPassword(), getGender(), getRegistrationDate());
    }
}
