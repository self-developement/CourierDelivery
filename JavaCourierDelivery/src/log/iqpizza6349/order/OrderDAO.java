package log.iqpizza6349.order;

import java.util.HashMap;
import java.util.Map;

public class OrderDAO {

    private static final Map<Long, Order> ORDERS = new HashMap<>();

    public Order save(Order order) {
        long id = (order.getId() == null) ? 0 : order.getId();
        ORDERS.put(id, order);
        return ORDERS.get(id);
    }
}
