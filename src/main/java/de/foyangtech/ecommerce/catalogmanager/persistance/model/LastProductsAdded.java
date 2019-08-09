package de.foyangtech.ecommerce.catalogmanager.persistance.model;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LastProductsAdded  {

    public static int getMAX() {
        return MAX;
    }

    private  static final int MAX = 5;

    public List<Product> getLastAdded() {
        return lastAdded;
    }

    private List<Product> lastAdded = new ArrayList<>();



    public LastProductsAdded() {
    }

    public void addToLastest(Product product) {
        if(this.lastAdded.size() == MAX){
            for(int i = 0; i < MAX - 1; i++){
                this.lastAdded.set(i, this.lastAdded.get(i + 1));
            }
            this.lastAdded.set(MAX - 1, product);
        } else {
            this.lastAdded.add(product);
        }
    }


}
