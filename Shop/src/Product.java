
public class Product {
 private String name;
 private double price;
 private int quantity;
 private boolean expires;
 private boolean isExpired;

 public Product(String name, double price, int quantity,boolean expires, boolean isExpired){
	 this.name=name;
	 this.price=price;
	 this.quantity=quantity;
	 this.expires=expires;
	 if (expires && isExpired)
	 this.isExpired=isExpired;
 }
 public String getName(){
	 return name;
 }
 
 public double getPrice(){
	 return price;
 }
 
 public int getQuantity(){
	 return quantity;
 }
 
 public boolean doesExpire(){
	 return expires;
 }
 public boolean isExpired(){
	 return isExpired;
 }
 public void setExpired(boolean expired){
	 if (expires){
		 isExpired=expired;
	 }
 }
 public void setQuantity(int newQuantity){
	 quantity=newQuantity;
 }
}
 