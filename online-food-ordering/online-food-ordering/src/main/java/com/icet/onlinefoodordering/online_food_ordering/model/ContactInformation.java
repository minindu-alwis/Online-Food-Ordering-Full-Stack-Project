package com.icet.onlinefoodordering.online_food_ordering.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContactInformation {

    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
}
