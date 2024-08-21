package com.br.idonate.service;

import com.br.idonate.model.Foundation;
import com.br.idonate.model.Item;
import com.br.idonate.repository.FoundationRepository;
import com.br.idonate.repository.ItemRepository;
import com.br.idonate.searchCriteria.FoundationSearchCriteria;
import com.br.idonate.searchCriteria.ItemSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FoundationService {

    private final FoundationRepository foundationRepository;

    public FoundationService(FoundationRepository foundationRepository) {
        this.foundationRepository = foundationRepository;
    }

    public Page<Foundation> findFoundations(FoundationSearchCriteria criteria, int pageNumber, int pageSize){
        Specification<Foundation> spec = Specification.where(null);

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        return foundationRepository.findAll(spec, PageRequest.of(pageNumber, pageSize, sort));
    }


    public Foundation save(Foundation foundation){
        return foundationRepository.save(foundation);
    }

    public Foundation delete(Foundation foundation){
        foundationRepository.delete(foundation);
        return foundation;
    }

}
