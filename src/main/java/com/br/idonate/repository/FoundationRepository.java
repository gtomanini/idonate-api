package com.br.idonate.repository;

import com.br.idonate.model.Foundation;
import com.br.idonate.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
//public interface ItemRepository extends BaseRepository<Item, Long> {
public interface FoundationRepository extends JpaRepository<Foundation, Long>, JpaSpecificationExecutor<Foundation> {

}
