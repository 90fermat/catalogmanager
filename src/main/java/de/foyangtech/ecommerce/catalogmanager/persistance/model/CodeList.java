package de.foyangtech.ecommerce.catalogmanager.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeList {

    List<String> codes;

    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> persons) {
        this.codes= persons;
    }
}
