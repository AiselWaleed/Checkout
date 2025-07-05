import java.util.ArrayList;

import Exception.EmptyCartException;
import Exception.InsufficientBalanceException;
import Exception.UnavailableProductException;


public class Shopping {
	public static ArrayList <Integer> shippableItemsAmounts= new ArrayList <Integer>();
	
	public static void checkout(Customer customer, Cart cart){
		double shipping=0;
		boolean addShipping=false;
		double subtotal=0;
		int productAmount=0;
		double productTotal=0;
		double total=0;
		
		try{ 
			System.out.println("****"+customer.getName()+"'s Checkout Summary"+"****");
			if (cart.getItems().isEmpty()){
				throw new EmptyCartException("Error! "+customer.getName()+"'s Cart is Empty.");
			}
			
			//Processing Shippable Products
			
			Product current;
			ArrayList <Shippable> shippableItems= new ArrayList <Shippable> ();
			
			for (int i=0;i<cart.getSize(); i++){
				current= (Product)(cart.getItems().get(i));
				if (current instanceof ShippableProduct){
					Shippable shippableProduct= (Shippable) (current);
					shippableItems.add(shippableProduct);
					shippableItemsAmounts.add(cart.getAmounts().get(i));
				}
			}
			
			//Processing All Products, Calculating Subtotal.
			
			for (int i=0;i<cart.getItems().size(); i++){
				current=(Product)(cart.getItems().get(i));
				if (current.isExpired())
					throw new UnavailableProductException("Error! "+ current.getName()+" is Expired!");
				productAmount=cart.getAmounts().get(i);
				productTotal= current.getPrice()*productAmount;
				subtotal+=productTotal;
				
			}
			if(shippableItems.size()!=0){
				addShipping=true;
				shipping=30;
				
			}
			
			
			total=subtotal+shipping;
			
			if (customer.getBalance()<total){
				throw new InsufficientBalanceException("Error! Customer's balance is insufficient.");
			}
			else{
				customer.setBalance(customer.getBalance()-total);
			}
			
			//Displaying Shipped Items
			if(shippableItems.size()!=0){
				System.out.println("** Shipment Notice **");
				addShipping=true;
				shippingService(shippableItems);
			}
			
			System.out.println("");
			
			//Displaying Checkout Items
			System.out.println("** Checkout Receipt **");
			printOutProducts(cart);
			System.out.println("----------------------");
			
			//Displaying Payment Summary
			System.out.println(formatString("SubTotal",subtotal));
			if (addShipping)
				System.out.println(formatString("Shipping",shipping));
			System.out.println(formatString("Total",total));
			System.out.println("");

		

		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			System.out.println(customer.getName()+"'s Current Balance= "+customer.getBalance());
			System.out.println("End Of Summary.");
			System.out.println("");
		}
		
		
		
	}
	public static void printOutProducts(Cart cart){
		Product current;
		int productAmount=0;
		double productTotal=0;
		for (int i=0;i<cart.getItems().size(); i++){
			current=(Product)(cart.getItems().get(i));
			productAmount=cart.getAmounts().get(i);
			productTotal= current.getPrice()*cart.getAmounts().get(i);
			System.out.println (formatProduct(productAmount, current.getName(),productTotal));
		}
		
	}
	
	public static String formatString(String name, double calculation){
		String output=name+calculation;
		int gapSize=40-output.length();
		String gap=" ";
		for (int i=0; i<gapSize-1; i++){
			gap+=" ";
		}
		return name+gap+calculation;
	}
	
	public static String formatProduct(int amount, String name, double calculation){
		String output=amount+"x"+name+calculation;
		int gapSize=40-output.length();
		String gap=" ";
		for (int i=0; i<gapSize-1; i++){
			gap+=" ";
		}
		return amount+"x "+name+gap+calculation;
	}
		
	
	//INQUIRY: if a product is out of stock, it should be add that errors, correct?
	
	
	public static void shippingService( ArrayList <Shippable> shippableItems){
		Shippable current;
		int productAmount=0;
		double productTotalWeight=0;
		double totalShippedWeight=0;
		for (int i=0;i<shippableItems.size(); i++){
			current=shippableItems.get(i);
			productAmount=shippableItemsAmounts.get(i);
			productTotalWeight= current.getWeight()*shippableItemsAmounts.get(i);
			totalShippedWeight+=productTotalWeight;
			System.out.println(formatProduct(productAmount,((Product)(current)).getName(),productTotalWeight));
		}
		System.out.println(formatString("Total Package Weight =",totalShippedWeight/1000.0)+"kg");
		
	}
	
	
	public static void main (String [] args){	
		Product tV= new Product("TV", 50000.0, 30, false, false);
		Product mobile= new Product("Mobile Phone", 10000.0, 20, false, false);
		Product plasticBag= new Product("Plastic Bag", 3.0, 100, false, false);
		Product scratchCard= new Product("Scratch Card", 20.0,40, false, false);
		ShippableProduct biscuits=new ShippableProduct("Biscuits", 50.0, 25, true, false, 400);
		ShippableProduct cheese=new ShippableProduct("Cheese", 50.0, 10, true, false, 500);
		ShippableProduct milk=new ShippableProduct("Milk", 60.0, 15, true, true, 250);
		ShippableProduct strawberries=new ShippableProduct("Strawberries", 55.0, 20, true, false, 250);
		ShippableProduct tomatosauce=new ShippableProduct("Tomato Sauce",40.0, 5, true, false,150);
		Product honey= new Product("Honey", 30.0, 0, true, false);
		//Example 1 : Successful Checkout
		Customer omar= new Customer("Omar",65000);
		Cart omarscart= new Cart();

		omarscart.add(tV, 1);
		omarscart.add(cheese, 2);
		omarscart.add(strawberries, 1);
		
		checkout(omar, omarscart);
		shippableItemsAmounts.clear();
		
		//Example 2 : Insufficient Balance
		Customer dina= new Customer("Dina",500);
		Cart dinascart= new Cart();
		
		dinascart.add(cheese, 4);
		dinascart.add(strawberries, 2);
		dinascart.add(scratchCard, 2);
		dinascart.add(tomatosauce, 4);
		
		checkout(dina, dinascart);
		shippableItemsAmounts.clear();
		
		//Example 3: Product Out of Stock: customer is informed, cart is checked out normally without the unavailable product. 
		Customer farida= new Customer("Farida",3750);
		Cart faridascart= new Cart();	
		faridascart.add(strawberries, 2);
		faridascart.add(honey, 3);
		
		checkout(farida, faridascart);
		shippableItemsAmounts.clear();
		
		//Example 4: Required quantity is more than that's available for the product:customer is informed, cart is checked out normally without the unavailable product. 
		Customer ali= new Customer("Ali",1400);
		Cart aliscart= new Cart();
		aliscart.add(biscuits, 3);
		aliscart.add(tomatosauce, 7);
						
		checkout(ali, aliscart);
		shippableItemsAmounts.clear();

		//Example 5: Expired Product: customer is warned, checkout is terminated.
		Customer adham= new Customer("Adham",15000);
		Cart adhamscart= new Cart();
		
		adhamscart.add(mobile, 1);
		adhamscart.add(milk, 3);
		
		checkout(adham, adhamscart);
		shippableItemsAmounts.clear();
		
		
		//Example 6: Empty Cart
		Customer dummy= new Customer("Dummy", 450);
		Cart dummyscart= new Cart();
		
		checkout(dummy, dummyscart);

		
		
	}
}
