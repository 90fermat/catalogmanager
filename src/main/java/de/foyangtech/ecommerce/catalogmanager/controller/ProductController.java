package de.foyangtech.ecommerce.catalogmanager.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import de.foyangtech.ecommerce.catalogmanager.error.exception.ProductIdMismatchException;
import de.foyangtech.ecommerce.catalogmanager.error.exception.ProductNotFoundException;
import de.foyangtech.ecommerce.catalogmanager.persistance.dao.ProductDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private Product product;


    /**
     * return all products
     */
    @GetMapping
    public MappingJacksonValue listProducts() {
        Iterable<Product> products = productDao.findAll();

        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("buyingPrice");

        FilterProvider listOfFilters = new SimpleFilterProvider().addFilter("myDynamicFilter", myFilter);

        MappingJacksonValue productsFilters = new MappingJacksonValue(products);

        productsFilters.setFilters(listOfFilters);

        return productsFilters;
    }

    /**
     *  return a product by his name
     * @param name of the product
     * @return  the product
     */
    @GetMapping("/{name}")
    public Product findByName(@PathVariable String name) {
        return productDao.findByName(name);
    }

    /**
     *  retuen a product by his id
     * @param id of the product
     * @return the product
     */
    @GetMapping("/{id}")
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
    public String create(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {

        product.setTimestamp(new Date());

        if (bindingResult.hasErrors()) {
            return "/createProduct";
        }
        Product productAdded = productDao.save(product);
        if (productAdded == null) {
            return "/createProduct";
        }
         model.addAttribute("productAdded", product);
        return "/succesCreated";

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
       productDao.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productDao.deleteById(id);
    }

    @PutMapping("/{id}")
    public Product updateBook(@RequestBody Product product, @PathVariable Long id) {
        if (product.getId() != id) {
            throw new ProductIdMismatchException();
        }
        productDao.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        return productDao.save(product);
    }

    @GetMapping("/{nameLike}")
    public List<Product> requestName(@PathVariable String nameLike) {
        return productDao.findByNameLike("%" + nameLike + "%");
    }

    @GetMapping("/{limitPrice}")
    public List<Product> limitPrice(@PathVariable int limitPrice) {
        return productDao.findByBuyingPriceGreaterThan(limitPrice);
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("product", product);
        model.addAttribute("created", false);
        return "createProduct";
    }
}
