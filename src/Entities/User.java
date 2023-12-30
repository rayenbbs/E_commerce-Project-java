package Entities;

public abstract class User { // user class that contains the userID,Password,The role(customer or admin)
    private String userID;

    private String password;

    private String role;
    public User(String userID, String password,String role)
    {
        this.userID=userID;
        this.password=password;
        this.role=role;
    }

    public String getRole() {
        return this.role;
    }

    public String getUserID()
    {
        return (this.userID);
    }

    public String getPassword()
    {
        return (this.password);
    }



}
