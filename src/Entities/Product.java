package Entities;

public abstract class Product { //Product class containing the barCode of the product(ID) the name,price,quantity,promotionAdded(boolean)
    //This class is abstract as there are 2 abstract methods (showProduct(description of the product) and getCategory(electronics or clothing in this case))
    //these two methods are implemented by ClothingProduct and ElectronicsProduct classes
    private String name;
    private int barCode;

    private int price;

    private int inStock;
    boolean promotionAdded=false;

    public Product(String name,int barCode,int price,int inStock)
    {
        this.name=name;
        this.barCode=barCode;
        this.price=price;
        this.inStock=inStock;
    }
    public void setPromotionAdded(){
        promotionAdded=true;
    }
    public boolean getPromotionAdded(){
        return this.promotionAdded;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName()
    {
        return this.name;
    }

    public int getBarCode(){
        return this.barCode;
    }
    public void setBarCode(int barCode){
        this.barCode=barCode;
    }

    public int getPrice(){
        return this.price;
    }

    public void setPrice(int price){
        this.price=price;
    }

    public int getInStock(){
        return this.inStock;
    }

    public void setInStock(int inStock){
        this.inStock=inStock;
    }

    public abstract void showProduct();
    public abstract String getCategory();

}
