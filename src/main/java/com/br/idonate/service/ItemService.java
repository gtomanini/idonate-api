package com.br.idonate.service;

import com.br.idonate.dto.ItemDTO;
import com.br.idonate.mapper.ItemMapper;
import com.br.idonate.model.Item;
import com.br.idonate.repository.ItemRepository;
import com.br.idonate.searchCriteria.ItemSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService{

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Page<ItemDTO> findItems(ItemSearchCriteria criteria, int pageNumber, int pageSize){
        Specification<Item> spec = Specification.where(null);

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Page<Item> items = itemRepository.findAll(spec, PageRequest.of(pageNumber, pageSize, sort));

        List<ItemDTO> itemDTOs = items.getContent().stream()
                .map(ItemMapper::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(itemDTOs, items.getPageable(), items.getTotalElements());
//        return itemRepository.findAll(spec, PageRequest.of(pageNumber, pageSize, sort));
    }


    public Item save(Item item){
        return itemRepository.save(item);
    }

    public Item delete(Item item){
        itemRepository.delete(item);
        return item;
    }

}
