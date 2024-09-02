package com.br.idonate.mapper;

import com.br.idonate.dto.ItemDTO;
import com.br.idonate.model.Item;

public class ItemMapper {
    public static ItemDTO toDTO(Item item) {
        return new ItemDTO(item.getId(), item.getName(), item.getDescription());
    }

    public static Item toEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.id());
        item.setName(itemDTO.name());
        item.setDescription(itemDTO.description());
        return item;
    }
}
