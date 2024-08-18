package com.br.idonate.service;

import com.br.idonate.model.Item;
import com.br.idonate.repository.ItemRepository;
import com.br.idonate.searchCriteria.ItemSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class ItemService{

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Page<Item> findItems(ItemSearchCriteria criteria, int pageNumber, int pageSize){
        Specification<Item> spec = Specification.where(null);

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        return itemRepository.findAll(spec, PageRequest.of(pageNumber, pageSize, sort));
    }


    public Item save(Item item){
        return itemRepository.save(item);
    }

}
