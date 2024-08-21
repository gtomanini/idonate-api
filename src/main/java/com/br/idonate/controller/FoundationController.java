package com.br.idonate.controller;


import com.br.idonate.model.Foundation;
import com.br.idonate.model.Item;
import com.br.idonate.searchCriteria.FoundationSearchCriteria;
import com.br.idonate.searchCriteria.ItemSearchCriteria;
import com.br.idonate.service.FoundationService;
import com.br.idonate.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/foundations")
public class FoundationController {

    @Autowired
    private FoundationService foundationService;

    @GetMapping
    public ResponseEntity<Page<Foundation>> index(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                  @RequestParam(required = false, defaultValue = "10") int pageSize) {

        FoundationSearchCriteria criteria = new FoundationSearchCriteria();
        return ResponseEntity.ok(foundationService.findFoundations(criteria, pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<Foundation> create(@RequestBody Foundation foundation) {
        return ResponseEntity.ok(foundationService.save(foundation));
    }

    @PutMapping
    public ResponseEntity<Foundation> update(@RequestBody Foundation foundation) {
        return ResponseEntity.ok(foundationService.save(foundation));
    }

    @DeleteMapping
    public ResponseEntity<Foundation> delete(@RequestBody Foundation foundation) {
        return ResponseEntity.ok(foundationService.delete(foundation));
    }

}
