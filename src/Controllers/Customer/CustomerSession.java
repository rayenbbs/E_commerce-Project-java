package Controllers.Customer;


import Controllers.UserSession;

public class CustomerSession extends UserSession {

    private static volatile CustomerSession instance;

    private CustomerSession(String identifier) //singleton design pattern because only one customer session should be instantiated at once
    {
        super(identifier);
    }

    public static CustomerSession getInstance(String identifier){ //singleton design pattern
        CustomerSession result=instance;
        if (result==null){
            synchronized (CustomerSession.class){
                result=instance;
                if (result==null){
                    instance = result = new CustomerSession(identifier);
                }
            }
        }
        return result;
    }

    @Override
    public void showWelcomePage() //show the welcome page of the custoùer
    {
        HomeControllerCustomer homeControllerCustomer = new HomeControllerCustomer(this);
        homeControllerCustomer.showWelcomePage();
    }
}
