package log.iqpizza6349.springcourierdelivery.domain.order.dao;

import log.iqpizza6349.springcourierdelivery.domain.order.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
