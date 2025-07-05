import java.util.ArrayList;

import Exception.UnavailableProductException;


public class Cart {
 private ArrayList <Product> shoppingCart;
 private ArrayList <Integer> amounts;
 public Cart(){
		shoppingCart= new ArrayList <Product> ();
		amounts= new ArrayList <Integer> ();
 }
 public ArrayList <Product> getItems(){
	 return shoppingCart;
 }
 public ArrayList <Integer> getAmounts(){
	 return amounts;
 }
	
 public void add(Product product, int amount){
	 try {
		 if (product.getQuantity()==0 && amount!=0)
			 throw new UnavailableProductException(product.getName() +" is out of stock!");

		 if (amount>product.getQuantity())
			 throw new UnavailableProductException(product.getName()+" is unavailable in the required quantity!");

		 product.setQuantity(product.getQuantity()-amount);
		 shoppingCart.add(product);
		 amounts.add(amount);
		 }
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	
	 }
	
 
	
 public int getSize(){
	 return shoppingCart.size();
 }
	
	
}
