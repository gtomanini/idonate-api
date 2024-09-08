package com.br.idonate.mapper;

import com.br.idonate.dto.ItemDTO;
import com.br.idonate.model.Category;
import com.br.idonate.model.Item;

import java.util.stream.Collectors;

public class ItemMapper {
    public static ItemDTO toDTO(Item item) {
        return new ItemDTO(item.getId(), item.getName(), item.getDescription(), item.getCategories().stream().map(CategoryMapper::toDTO).collect(Collectors.toList()));
    }

    public static Item toEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.id());
        item.setName(itemDTO.name());
        item.setDescription(itemDTO.description());
        return item;
    }
}

