package com.br.idonate.controller;


import com.br.idonate.model.Item;
import com.br.idonate.searchCriteria.ItemSearchCriteria;
import com.br.idonate.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private  ItemService itemService;

    @GetMapping
    public ResponseEntity<Page<Item>> index(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        ItemSearchCriteria criteria = new ItemSearchCriteria();
        return ResponseEntity.ok(itemService.findItems(criteria, pageNumber, pageSize));
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
