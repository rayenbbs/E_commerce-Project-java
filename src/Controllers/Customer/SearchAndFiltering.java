package Controllers.Customer;

import Controllers.Customer.ProductMenuCustomer;
import Controllers.Customer.ShoppingCartProductSelectedMenu;
import Controllers.UserSession;
import Entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchAndFiltering { //This is the SearchAndFiltering class that filters Products depending on the chosen filter
    //The filters I chose were Filter by Price(User inputs a price Range and I filter products according to that price Range)
    //Filter by category: Show products of a certain category.
    //User can also search for a product using a keyword and the product.
    public List<Product> productList;
    UserSession userSession;

    public SearchAndFiltering(List<Product> productList,UserSession userSession)//This class has ProductList attribute and UserSession attribute
    {
        this.productList=productList;
        this.userSession=userSession;
    }
    public void setProductList(List<Product> productList){
        this.productList=productList;
    }
    public List<Product> searchProducts(String keyword) { //Search By Keyword
        List<Product> Result = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                Result.add(product);
            }
        }
        return Result;
    }
    public List<Product> searchByCategory(String category){//Filter By category
        List<Product> Result = new ArrayList<>();
        for (Product product : productList){
            if (product.getCategory().toLowerCase().equals(category.toLowerCase()))
                Result.add(product);
        }
        return(Result);
    }
    public List<Product> searchByPrice(int minPrice, int maxPrice){ //Filter By Price
        List<Product> Result = new ArrayList<>();
        for (Product product : productList){
            if (product.getPrice()<=maxPrice && product.getPrice()>=minPrice)
                Result.add(product);
        }
        return(Result);
    }
    public void showSearchFilteringMenu()//This is the SearchAndFilter Menu, User chooses the filters and search option he wants
    {
        System.out.println("1- Search by name: ");
        System.out.println("2- Filter by category: ");
        System.out.println("3- Filter by price: ");
        System.out.println("4- Return: ");
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        scanner.nextLine();
        List <Product> result=null;
        ProductMenuCustomer productMenuCustomer = new ProductMenuCustomer(userSession);
        boolean test=true;
        switch (choix){
            case 1:{
                System.out.println("Type in the name: ");
                String keyword = scanner.nextLine() ;
                result = searchProducts(keyword);
                if (result.isEmpty()){ // The result list is empty
                    test=false;
                    System.out.println("There are no products having this name!");
                    showSearchFilteringMenu();
                }
                break;
            }
            case 2:
            {
                System.out.println("Type in the category: ");
                String category = scanner.nextLine() ;
                result = searchByCategory(category);
                if (result.isEmpty()){ // The result list is empty
                    test=false;
                    System.out.println("There are no products having this category!");
                    showSearchFilteringMenu();
                }
                break;
            }
            case 3:{
                System.out.println("Type in the minPrice: ");
                int minPrice = scanner.nextInt();
                scanner.nextLine() ;
                System.out.println("Type in the maxPrice: ");
                int maxPrice = scanner.nextInt();
                scanner.nextLine() ;
                result = searchByPrice(minPrice,maxPrice);
                if (result.isEmpty()) // The result list is empty
                {
                    test=false;
                    System.out.println("There are no products in this price range!");
                    showSearchFilteringMenu();
                }
                break;
            }
            case 4:{ //return to the product menu
                test=false;
                productMenuCustomer.showProductMenu();
            }
            default:{
                test=false;
                System.out.println("Wrong choice!");
                showSearchFilteringMenu();
            }
        }
        if (test) // The result list isn't empty
        {
            System.out.println("Filter Applied: Select your product:");
            System.out.println("0- Return");

            for (int i = 0; i < result.size(); ++i) {
                System.out.println(i + 1 + "- Name:" + result.get(i).getName() + " Price: " + result.get(i).getPrice() + " Quantity: " + result.get(i).getInStock());
            }
            int choix2 = scanner.nextInt();
            scanner.nextLine();
            if (choix2==0){ //return to product menu
                productMenuCustomer.showProductMenu();
            }
            else { // User chose an item
                ShoppingCartProductSelectedMenu shoppingCartProductSelectedMenu = new ShoppingCartProductSelectedMenu(result.get(choix2 - 1), this.userSession);
                shoppingCartProductSelectedMenu.showProductSelectedMenu();
            }
        }
    }
}
