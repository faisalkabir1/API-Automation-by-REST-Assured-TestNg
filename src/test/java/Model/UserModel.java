package Model;

public class UserModel {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String phoneNumber;
    public String address;
    public String gender;
    public boolean termsAccepted;

    public UserModel(String firstName, String lastName, String email, String password,
                     String phoneNumber, String address, String gender, boolean termsAccepted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.termsAccepted = termsAccepted;
    }
}