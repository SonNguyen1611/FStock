
package com.example.Fstock.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false)
    private Integer categoryId;

    @Column( nullable = false )
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "category")
    private List<Product> products;

}
