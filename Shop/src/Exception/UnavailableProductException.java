package Exception;

public class UnavailableProductException extends Exception {
	public UnavailableProductException(){
		super();
	}
	public UnavailableProductException(String message){
		super(message);
	}
}
