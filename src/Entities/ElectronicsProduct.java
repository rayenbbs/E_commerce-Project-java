package Entities;

public class ElectronicsProduct extends Product{ //This is the electronics Product class extending the abstract class Product
    //This class implements the showProduct and getCategory methods.
    //This class has two other attributes other than the ones in the Product class(type and brand);

    private String type;
    private String brand;

    public ElectronicsProduct(String name,int barCode,int price,int inStock,String type,String brand){
        super(name, barCode, price, inStock);
        this.type=type;
        this.brand=brand;
    }

    public String getBrand(){
        return this.brand;
    }
    public String getType(){
        return this.type;
    }
    public void setType(String type)
    {
        this.type=type;
    }
    public void setBrand(String brand){
        this.brand=brand;
    }

    public void showProduct (){
        System.out.println("This is an electronics product:");
        System.out.println("Name: "+getName());
        System.out.println("Price: "+getPrice());
        System.out.println("Stock: "+getInStock());
        System.out.println("Type: "+getType());
        System.out.println("Brand: "+getBrand());
    }
    public String getCategory(){
        return "Electronics";
    }

}
