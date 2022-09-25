package log.iqpizza6349.springcourierdelivery.domain.item.dao;

import log.iqpizza6349.springcourierdelivery.domain.item.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
}
