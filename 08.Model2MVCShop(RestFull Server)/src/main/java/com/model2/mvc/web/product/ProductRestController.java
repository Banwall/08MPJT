package com.model2.mvc.web.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==> 회원관리 RestController
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
		
	public ProductRestController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping( value="json/getProduct/{prodNo}",method=RequestMethod.GET)
	public Product getProduct( @PathVariable int prodNo) throws Exception {
		
		System.out.println("/product/json/getProduct : GET");
		
		return productService.getProduct(prodNo);
	}
	
	@RequestMapping( value="json/getProduct",method=RequestMethod.POST)
	public Product getProduct( @RequestBody Product product) throws Exception {
		
		System.out.println("/product/json/getProduct : POST");
		
		return productService.getProduct(product.getProdNo());
	}
	
	@RequestMapping(value="json/addProduct",method=RequestMethod.POST)
	public Product addProduct( @RequestBody Product product)throws Exception {
		
		System.out.println("/product/json/addProduct : POST");
		
		productService.addProduct(product);

		return product;
	}
	
	@RequestMapping(value="json/updateProductView/{prodNo}",method=RequestMethod.GET)
	public Product updateProduct( @PathVariable int prodNo)throws Exception {
		
		System.out.println("/product/json/updateProductView : GET ");
		
		return productService.getProduct(prodNo);
	}
}