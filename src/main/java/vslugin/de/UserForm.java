package vslugin.de;

public class UserForm {
    private String name;
    private String birthDate;   // ISO: "2000-10-01"
    private String email;
    private String phone;
    private String inn;
    private String passport;


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getInn() { return inn; }
    public void setInn(String inn) { this.inn = inn; }

    public String getPassport() { return passport; }
    public void setPassport(String passport) { this.passport = passport; }
}
