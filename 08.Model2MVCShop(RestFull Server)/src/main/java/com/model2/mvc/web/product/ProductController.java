package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;


//==> ȸ������ Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping( value = "addProduct" , method=RequestMethod.GET )
	public String addProductView() throws Exception {

		System.out.println("/product/addProduct : GET ");
		
		return "forward:/product/addProductView.jsp";
	}
	
	@RequestMapping( value = "addProduct" , method=RequestMethod.POST )
	public String addProduct( @ModelAttribute("product") Product product ) throws Exception {
		
		System.out.println("/product/addProduct : POST ");
		//Business Logic
		productService.addProduct(product);
		
		System.out.println("�Ƹ� ? �߰� �� product :: " + product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping( value = "getProduct" , method=RequestMethod.GET )
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model , @RequestParam("menu") String menu ) throws Exception {
		
		System.out.println("/product/getProduct : GET ");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("product", product);
		model.addAttribute("menu", menu);
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping( value = "updateProduct" , method=RequestMethod.GET )
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/product/updateProduct : GET ");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping( value = "updateProduct" , method=RequestMethod.POST )
	public String updateProduct( @ModelAttribute("product") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/product/updateProduct : POST ");
		//Business Logic
		productService.updateProduct(product);
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo()+"&menu=manage";
	}
	
	@RequestMapping( value = "listProduct" )
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request , 
							@RequestParam("menu") String menu ) throws Exception{

		System.out.println("/product/listProduct : GET / POST ");
		
		if( search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("menu", menu);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
}