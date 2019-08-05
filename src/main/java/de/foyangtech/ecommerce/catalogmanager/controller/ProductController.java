package de.foyangtech.ecommerce.catalogmanager.controller;

import de.foyangtech.ecommerce.catalogmanager.error.exception.ProductIdMismatchException;
import de.foyangtech.ecommerce.catalogmanager.error.exception.ProductNotFoundException;
import de.foyangtech.ecommerce.catalogmanager.persistance.dao.ProductDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.Product;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.type.BlobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import static java.sql.Types.BLOB;

@Controller
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private Product product;




/*    *//**
     * return all products
     *//*
    @GetMapping
    public String listProducts(Model model) {
        Iterable<Product> products = productDao.findAll();

        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("buyingPrice");

        FilterProvider listOfFilters = new SimpleFilterProvider().addFilter("myDynamicFilter", myFilter);

        MappingJacksonValue productsFilters = new MappingJacksonValue(products);

        productsFilters.setFilters(listOfFilters);

        model.addAttribute("products", productsFilters);

        return "listProducts";
    }*/

    @GetMapping
    public String allProducts(Model model) {
        List<Product> products = productDao.findAll();
        model.addAttribute("products", products);

        return "listProducts";
    }

    /**
     *  return a product by his name
     * @param name of the product
     * @return  the product
     */
    @GetMapping("name/{name}")
    public Product findByName(@PathVariable String name) {
        return productDao.findByName(name);
    }

    /**
     *  retuen a product by his id
     * @param id of the product
     * @return the product
     */
    @GetMapping("ids/{id}")
    public Product findById(@PathVariable  Long id) {
        return productDao.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }
/*
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@Valid @ModelAttribute Product product, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return
        }
        Product productAdded = productDao.save(product);
        if(productAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }*/

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @ModelAttribute Product product,
                         BindingResult bindingResult, Model model) {


        product.setTimestamp(new Date());

        product.setImageName(product.getMultipartFile().getOriginalFilename());

        try {
             product.setPhoto(product.getMultipartFile().getBytes());
        } catch (IOException io){
            System.out.println("Error append by trying to transfer MultipartFile and " +
                    io.getMessage());
        }




        if (bindingResult.hasErrors()) {
            return "createProduct";
        }
        Product productAdded = productDao.save(product);
        if (productAdded == null) {
            return "createProduct";
        }
         model.addAttribute("productAdded", product);
         model.addAttribute("created", true);

        return "createProduct";

    }

    @DeleteMapping("ids/{id}")
    public void delete(@PathVariable Long id) {
       productDao.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productDao.deleteById(id);
    }

    @PutMapping("ids/{id}")
    public Product updateBook(@RequestBody Product product, @PathVariable Long id) {
        if (product.getId() != id) {
            throw new ProductIdMismatchException();
        }
        productDao.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        return productDao.save(product);
    }

    @GetMapping("name/{nameLike}")
    public List<Product> requestName(@PathVariable String nameLike) {
        return productDao.findByNameLike("%" + nameLike + "%");
    }

    @GetMapping("price/{limitPrice}")
    public List<Product> limitPrice(@PathVariable int limitPrice) {
        return productDao.findByBuyingPriceGreaterThan(limitPrice);
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {


        model.addAttribute("product", product);
        model.addAttribute("created", false);
        return "createProduct";
    }

    @GetMapping("/imageDisplay")
    public void showImages(@RequestParam("id") Integer id, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException, SQLException {
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        //byte[] bytes = Base64.decodeBase64(productDao.findPhotoById(id));

       // byte[] bytes = ph.getBytes(1, (int) ph.);
      //  InputStream inputStream = new ByteArrayInputStream(bytes);
       // IOUtils.copy(inputStream, response.getOutputStream());

        response.getOutputStream().write(productDao.findPhotoById(id));
        response.getOutputStream().close();
    }

}
