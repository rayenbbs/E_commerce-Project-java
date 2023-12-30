package Entities;

import java.util.List;

public class ShoppingCart { //The shopping cart class containing a list of the products

    private List<Product> productList;
    public ShoppingCart(List<Product> productList){
        this.productList=productList;
    }

    public void setProductList(List<Product>  productList){
        this.productList=productList;
    }

    public List<Product> getProductList(){
        return this.productList;
    }

}
