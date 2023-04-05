package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.entity.dto.CartMessage;
import com.example.demo.entity.dto.ProductDto;
import com.example.demo.entity.dto.ProductView;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
        @GetMapping("/product")
    public ResponseEntity<?> getProducts(@RequestParam(name = "page" , required = false , defaultValue = "1") int page , @RequestParam(name = "size") int size , @RequestParam(name = "domain") String domain , @RequestParam(name = "dir") String dir){
        List<Product> list = productService.findAll(size,page,domain,dir).getContent();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/product/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) throws UserNotFoundException {
            int id2 = Integer.parseInt(id);
            return new ResponseEntity<>(productService.findById(id2), HttpStatus.OK);
    }
    @GetMapping("/product/search")
    public ResponseEntity<?> searchProduct(@RequestParam(name = "page" , required = false , defaultValue = "1") int page , @RequestParam(name = "size" ,required = false , defaultValue = "6") int size,@RequestParam(name = "domain" , required = false , defaultValue = "productId") String domain , @RequestParam(name = "dir" ,required = false , defaultValue = "asc") String dir , @RequestParam(name = "keyword" ,required = false , defaultValue = "") String keyword){
        List<Product> list = productService.searchByProductName(size,page,domain,dir,keyword).getContent();
        int pageCount = productService.searchByProductName(size,page,domain,dir,keyword).getTotalPages();
        ProductView productView = new ProductView();
        productView.setProductList(list);
        productView.setPageCount(pageCount);
        return new ResponseEntity<>(productView,HttpStatus.OK);
    }
   // @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestParam(name = "productName") String productName  , @RequestParam(name = "image")MultipartFile file, @RequestParam(name = "price") String price , @RequestParam(name = "category") String category , @RequestParam(name = "description") String description) throws ObjectExistedException, IOException {
            ProductDto productDto = new ProductDto();
            productDto.setProductName(productName);
            productDto.setCategory(category);
            productDto.setDescription(description);
            productDto.setImage(file);
            productDto.setPrice(price);
            return new ResponseEntity<>(productService.create(productDto) , HttpStatus.CREATED);
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@RequestParam(name = "productName") String productName  , @RequestParam(name = "image" ,required = false)MultipartFile file, @RequestParam(name = "price") String price , @RequestParam(name = "category") String category , @RequestParam(name = "description") String description , @PathVariable int id) throws UserNotFoundException, ObjectExistedException, IOException {
        ProductDto productDto = new ProductDto();
        productDto.setPrice(price);
        productDto.setProductName(productName);
        productDto.setCategory(category);
        productDto.setImage(file);
        productDto.setDescription(description);
        return  new ResponseEntity<>(productService.update(productDto,id) , HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) throws UserNotFoundException {
        try {
            return new ResponseEntity<>(productService.findById(id) , HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id ){
        try{
            productService.delete(id);
            return new ResponseEntity<>(new CartMessage("Da Xoa Thanh Cong " ), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(new CartMessage("Co loi xay ra") , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product/same-category/{id}")
    public ResponseEntity<?> getSameCategory(@PathVariable int id , @RequestParam(name = "page" , required = false , defaultValue = "1") int page , @RequestParam(name = "size" , defaultValue = "4") int size) throws UserNotFoundException {
            Product product = productService.findById(id);
            ProductView productView = new ProductView();
            productView.setProductList(productService.getSameCategory(product.getCategory().getCategoryId() , page , size).getContent());
            productView.setPageCount(productService.getSameCategory(product.getCategory().getCategoryId() , page , size).getTotalPages());
        return new ResponseEntity<>(productView, HttpStatus.OK);
    }

    @GetMapping("/product/lastestProduct")
    public ResponseEntity<?> getLastestProduct(){
        Pageable pageable1 = PageRequest.of(0,6);
            return new ResponseEntity<>(productService.getLastestProduct(pageable1) , HttpStatus.OK);
    }

    @GetMapping("/produt/productSale")
    public ResponseEntity<?> getProductSale(){
            return new ResponseEntity<>(productService.getProductSale(),HttpStatus.OK);
    }

    @GetMapping("/product/getAll")
    public ResponseEntity<?> getAll(){
            return new ResponseEntity<>(productService.getAll(),HttpStatus.OK);
    }
}
