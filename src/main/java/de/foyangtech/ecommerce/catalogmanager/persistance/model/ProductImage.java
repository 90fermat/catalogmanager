package de.foyangtech.ecommerce.catalogmanager.persistance.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import javax.imageio.IIOException;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Entity
@Component
@Table(name ="images")
public class ProductImage {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    private String fileName;

    private String fileType;

    @OneToOne(mappedBy = "image")
    private Product product;

    @Lob
    @Column(name = "photo")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;

    public ProductImage() {    }

    public ProductImage(String data) {
        Path path = Paths.get(data);
        fileName  = path.getFileName().toString();
        fileType = getExtension(this.fileName);

      try {
          this.data = Files.readAllBytes(path);
      } catch (IOException io) {
          System.out.println(io.getMessage());
      }
    }

    public String getFileName() {
        return fileName;
    }

    private String getExtension(String fileName) {
        String[] strings = fileName.split("\\.");
        String extension = strings[1];
        return extension;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
