package com.br.idonate.controller;


import com.br.idonate.dto.ItemDTO;
import com.br.idonate.model.Item;
import com.br.idonate.searchCriteria.ItemSearchCriteria;
import com.br.idonate.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private  ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDTO>> index(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                               @RequestParam(required = false, defaultValue = "10") int pageSize,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String description,
                                               @RequestParam(required = false) String category) {

        ItemSearchCriteria criteria = new ItemSearchCriteria(name, description, category);
        return ResponseEntity.ok(itemService.findItems(criteria, pageNumber, pageSize).stream()
                .collect(Collectors.toList()));
    }

    @GetMapping("/testa")
    public String retornaTesta()
    {
        return itemService.retorna("Feito");
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.save(item));
    }

    @PutMapping
    public ResponseEntity<Item> update(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.save(item));
    }

    @DeleteMapping
    public ResponseEntity<Item> delete(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.delete(item));
    }

}
