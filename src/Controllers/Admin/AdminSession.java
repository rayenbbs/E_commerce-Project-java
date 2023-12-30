package Controllers.Admin;

import Controllers.UserSession;

public class AdminSession extends UserSession {
    private static volatile AdminSession instance;

    private AdminSession(String identifier) //singleton design pattern because only one Admin session should be instantiated at once
    {
        super(identifier);
    }

    public static AdminSession getInstance(String identifier){ //singleton design pattern
        AdminSession result=instance;
        if (result==null){
            synchronized (AdminSession.class){
                result=instance;
                if (result==null){
                    instance = result = new AdminSession(identifier);
                }
            }
        }
        return result;
    }

    @Override
    public void showWelcomePage()//showing admin welcome page
    {
        HomeControllerAdmin homeControllerAdmin = new HomeControllerAdmin(this);
        homeControllerAdmin.showWelcomePage();
    }
}
