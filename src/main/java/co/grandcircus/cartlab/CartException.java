package co.grandcircus.cartlab;

public class CartException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public CartException(String id) {
		super("Could not find character with id " + id);
}

}


