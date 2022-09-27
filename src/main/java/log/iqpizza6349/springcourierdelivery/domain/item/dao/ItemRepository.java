package log.iqpizza6349.springcourierdelivery.domain.item.dao;

import log.iqpizza6349.springcourierdelivery.domain.item.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    @Query("select new java.lang.Long(COALESCE(i.amount, 0)) from Item i where i.name = ?1")
    Long findAmountByName(String name);

    Optional<Item> findByName(String name);
}
