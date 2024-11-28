package com.br.idonate.service;

import com.br.idonate.dto.ItemDTO;
import com.br.idonate.mapper.ItemMapper;
import com.br.idonate.model.Category;
import com.br.idonate.model.Item;
import com.br.idonate.repository.ItemRepository;
import com.br.idonate.searchCriteria.ItemSearchCriteria;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService{

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Page<ItemDTO> findItems(ItemSearchCriteria criteria, int pageNumber, int pageSize){
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Specification<Item> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(criteria.getName() != null){
                predicates.add(criteriaBuilder.like(root.get("name"), "%"+criteria.getName()+"%"));
            }
            if(criteria.getDescription() != null){
                predicates.add(criteriaBuilder.like(root.get("description"), "%"+criteria.getDescription()+"%"));
            }
            if(criteria.getCategory() != null){
                Join<Item, Category> categoryJoin = root.join("categories");
                predicates.add(categoryJoin.get("id").in(criteria.getCategory()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<Item> items = itemRepository.findAll(spec, PageRequest.of(pageNumber, pageSize, sort));

//        List<ItemDTO> itemDTOs = items.getContent().stream()
//                .map(ItemMapper::toDTO)
//                .collect(Collectors.toList());

        LinkedList<ItemDTO> itemDTOs = items.getContent().stream()
                .map(ItemMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));

        return new PageImpl<>(itemDTOs, items.getPageable(), items.getTotalElements());
    }


    public Item save(Item item){
        return itemRepository.save(item);
    }

    public Item delete(Item item){
        itemRepository.delete(item);
        return item;
    }

    public String retorna(String teste)
    {
        return teste;
    }

}
