package models;

public class Mentor {
    private int id;
    private String name;
    private String email;
    private String address;
    private String phoneNo;
    private String password;

    // Full Constructor
    public Mentor(int id, String name, String email, String address, String phoneNo, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    // Constructor without ID (useful for creating new mentor before insertion)
    public Mentor(String name, String email, String address, String phoneNo, String password) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString (helpful in combo box or tables)
    @Override
    public String toString() {
        return name + " - " + email;
    }

    public static Object stream() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stream'");
    }

    public static Mentor get(int selectedIndex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
}
