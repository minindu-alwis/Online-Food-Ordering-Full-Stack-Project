package com.icet.onlinefoodordering.online_food_ordering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredient_category")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    @JsonIgnore
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<IngredientsItem> ingredients=new ArrayList<>();

}
