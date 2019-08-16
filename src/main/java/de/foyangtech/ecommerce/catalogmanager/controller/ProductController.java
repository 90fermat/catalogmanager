package de.foyangtech.ecommerce.catalogmanager.controller;

import de.foyangtech.ecommerce.catalogmanager.error.exception.ProductIdMismatchException;
import de.foyangtech.ecommerce.catalogmanager.error.exception.ProductNotFoundException;
import de.foyangtech.ecommerce.catalogmanager.persistance.dao.CatalogDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.dao.ImageDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.dao.ProductDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.*;
import de.foyangtech.ecommerce.catalogmanager.service.ProductService;
import de.foyangtech.ecommerce.catalogmanager.service.ViewType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CatalogDao catalogDao;

    @Autowired
    private Catalog catalog;

    @Autowired
    private Product product;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ProductService productService;

    private Enum viewType = ViewType.LIST;

    private List<ProductImage> photos;

    private List<Product> products = new ArrayList<>();
   private  List<Catalog> catalogs = new ArrayList<>();



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


    /**
     * return all products
     */
    @GetMapping
    public String allProducts(@RequestParam(value = "view", required = false) String view, Model model) {

        products = productDao.findAll();
        catalogs = catalogDao.findAll();

        model.addAttribute("catalogs", catalogs);
        model.addAttribute("products", products);
        model.addAttribute("categories", Category.values());
        if(view == null || view.equals("list")){
            viewType = ViewType.LIST;
        } else {
            viewType = ViewType.IMAGE;
            photos = imageDao.findAll();
        }
        model.addAttribute("photos", photos);
        model.addAttribute("viewType", viewType);
        model.addAttribute("enumList", ViewType.LIST);
        model.addAttribute("enumImage", ViewType.IMAGE);

        return "list_products";
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
    public String createProduct(@Valid @ModelAttribute Product product,
                         BindingResult bindingResult, Model model) {

        //Image construct
        ProductImage image = new ProductImage();
        image.setFileName(product.getMultipartFile().getOriginalFilename());
        image.setFileType(getExtension(image.getFileName()));

        product.setTimestamp(new Date());

        try {
            image.setData(product.getMultipartFile().getBytes());
        } catch (IOException io){
            System.out.println("Error append by trying to transfer MultipartFile and " +
                    io.getMessage());
            model.addAttribute("badError", true);
            return "create_product";
        }

        if (bindingResult.hasErrors()) {
            return "create_product";
        }
        image.setProduct(product);
        product.setImage(image);
        Product productAdded = productService.addProduct(product);
        if (productAdded == null) {
            return "create_product";
        }
         model.addAttribute("productAdded", product);
         model.addAttribute("createdPR", true);

        return "create_product";

    }

    @PostMapping(value = "/create/catalog")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody String createCatalog(@RequestBody CodeList codes,
                                             BindingResult bindingResult, Model model) {
        Catalog catalog = new Catalog();
        if (bindingResult.hasErrors()) {
            return "/products/create?creation=catalog";
        }
        if (codes == null || codes.getCodes().isEmpty()) {
           /* catalog.setTitle(codes.getTitle());
            catalogDao.save(catalog);*/
           return "/products/create?creation=catalog";
        }
        codes.getCodes().stream()
                .forEach(code -> catalog.addProduct(productDao.findByCode(code)));
        catalog.setTitle(codes.getTitle());
        catalogDao.save(catalog);
        model.addAttribute("createdCA", true);

        return "/products/create?creation=catalog";

    }

    private String getExtension(String fileName) {
        String[] strings = fileName.split("\\.");
        String extension = strings[1];
        return extension;
    }

    @DeleteMapping("ids/{id}")
    public void delete(@PathVariable Long id) {
       productDao.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productDao.deleteById(id);
    }

    @PutMapping("ids/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable Long id) {
        if (product.getId() != id) {
            throw new ProductIdMismatchException();
        }
        productDao.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        return productService.addProduct(product);
    }

    @GetMapping("name/{nameLike}")
    public List<Product> requestName(@PathVariable String nameLike) {
        return productDao.findByNameLike("%" + nameLike + "%");
    }

    @GetMapping("categories/{category}")
    public List<Product> filterByCategory(@PathVariable String category) {
        return productDao.findByCategory(category);
    }

    @GetMapping("price/{limitPrice}")
    public List<Product> limitPrice(@PathVariable int limitPrice) {
        return productDao.findByBuyingPriceGreaterThan(limitPrice);
    }

    @GetMapping("/create")
    public String showCreateForm(Model model,
                                 @RequestParam(value = "creation", required = false)
                                         String creation) {

        if (creation == null) { creation = "product"; }

        products = productDao.findAll();
        List<String> catalogsTitle = catalogDao.findAllTile();

        List<String> productDescription = new ArrayList<>();
        for(Product product: products) {
            productDescription.add(product.toString());
        }

        List<String> productsName = productDao.findAllName();
        List<String> productsCode = productDao.findAllCode();
        List<String> prodToAdd = new ArrayList<>();

        model.addAttribute("productsCode", productsCode);
        model.addAttribute("prodToAdd", prodToAdd);
        model.addAttribute("catalogsTitle", catalogsTitle);
        model.addAttribute("productsName", productsName);
        model.addAttribute("productDescription", productDescription);

        model.addAttribute("creation", creation);
        model.addAttribute("catalog", catalog);
        model.addAttribute("product", product);
        model.addAttribute("created", false);
        return "create_product";
    }

    @GetMapping("/imageDisplay")
    public void showImage(@RequestParam("id") Integer id, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException, SQLException {
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");

        response.getOutputStream().write(imageDao.findDataById(productDao.findPhotoById(id)));
        response.getOutputStream().close();
    }

    @GetMapping("/imageDisplay_list")
    public void showImages(HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException, SQLException {
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");

        //byte[] bytes = Base64.decodeBase64(productDao.findPhotoById(id));
        // byte[] bytes = ph.getBytes(1, (int) ph.);
        //  InputStream inputStream = new ByteArrayInputStream(bytes);
        // IOUtils.copy(inputStream, response.getOutputStream());

        if(photos.iterator().hasNext()) {
            response.getOutputStream().write(photos.iterator().next().getData());
            response.getOutputStream().close();
        }
    }
}
