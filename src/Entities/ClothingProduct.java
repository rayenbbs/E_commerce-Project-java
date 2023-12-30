package Entities;

public class ClothingProduct extends Product{//This is the clothing Product class extending the abstract class Product
    //This class implements the showProduct and getCategory methods.
    //This class has two other attributes other than the ones in the Product class(type and size)
    private String type;

    private String size;

    public ClothingProduct(String name,int barCode,int price,int inStock,String type,String size){
        super(name, barCode, price, inStock);
        this.type=type;
        this.size=size;

    }


    public String getType(){
        return this.type;
    }
    public String getSize(){
        return this.size;
    }
    public void setType(String type)
    {
        this.type=type;
    }
    public void setSize(String size){
        this.size=size;
    }

    public void showProduct (){
        System.out.println("This is a Clothing product:");
        System.out.println("Name: "+getName());
        System.out.println("Price: "+getPrice());
        System.out.println("Stock: "+getInStock());
        System.out.println("Type: "+getType());
        System.out.println("Size: "+getSize());
    }
    public String getCategory(){
        return "Clothing";
    }
}
