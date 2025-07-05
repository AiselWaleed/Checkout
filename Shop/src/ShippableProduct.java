
public class ShippableProduct extends Product implements Shippable{
	
	private double weight; //in grams;
	
	public ShippableProduct(String name, double price, int quantity,boolean expires,boolean isExpired , double weight){
		super(name,price,quantity,expires, isExpired);
		this.weight=weight;
	}
	
	//the interface's String getName() is already inherited from the parent class.
	public double getWeight(){
		 return weight;
	}
	
}
