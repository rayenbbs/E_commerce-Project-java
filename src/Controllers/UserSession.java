package Controllers;

public abstract class UserSession { //This is the UserSession class that is instantiated after a successful login, it contains the userID attribute and the showWelcomePage method that shows a welcome page according to the UserSession.
    String userID;
    public UserSession(String userID)
    {
        this.userID=userID;
    }
    public String getUserID() {
        return userID;
    }


    public abstract void showWelcomePage(); //Shows the WelcomePage of the User after Logging in
}
