package co.grandcircus.cartlab;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CartController {
	@Autowired
	private CartRepository cart_repo;
	
	@GetMapping("/reset")
	public String reset() {
		
		// Delete all
		
		cart_repo.deleteAll();
		  
		  // Add characters
		  
		Cart cart1 = new Cart("Peaches",4.99,10); 
		cart_repo.insert(cart1);
		
		Cart cart2 = new Cart("Apples",1.99,100); 
		cart_repo.insert(cart2);
		
		return "Data reset.";

	}
	
	@GetMapping("/cart-items")
	public List<Cart> readAll(){
			return cart_repo.findAll();
		}
	
	
	@GetMapping("/cart-items/{id}")
	public Cart readOne(@PathVariable("id") String id) {

		return cart_repo.findById(id).orElseThrow(() -> new CartException(id));
	
	}
	
	@PostMapping("/cart-items")
	public Cart create(@RequestBody Cart cart) {
		cart_repo.insert(cart);
		return cart;
	}
	
	@PutMapping("/cart-items/{id}")
	public Cart update(@RequestBody Cart cart, @PathVariable("id") String id) {
		cart.setId(id);
		return cart_repo.save(cart);
	}
	
	
	@DeleteMapping("/cart-items/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		cart_repo.deleteById(id);
	}
	
	
	@GetMapping("/cart-items/total-cost")
	public Double totalCost() {
		double price = 0.0;
		
		for (Cart items: cart_repo.findAll()) {
			price += items.getPrice();
		}
		
		price *= 1.06;
		return price;
	}
	

	@PatchMapping("/cart-items/{id}/add")
	public Cart updateQuantity(@PathVariable("id") String id, @RequestParam int count) {
		Cart cart = cart_repo.findById(id).orElseThrow(() -> new CartException(id));
		cart.setQuantity(cart.getQuantity()+count);
		
		return cart_repo.save(cart);
	}
}



