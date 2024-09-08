package com.br.idonate.searchCriteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchCriteria {
    private String name;
    private String description;
    private String category;

    public ItemSearchCriteria(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
}
