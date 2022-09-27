package log.iqpizza6349.springcourierdelivery.domain.order.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import log.iqpizza6349.springcourierdelivery.domain.order.dto.OrderDto;
import log.iqpizza6349.springcourierdelivery.domain.order.entity.Order;
import lombok.Getter;

@Getter
public class OrderRO {

    private final String customer;

    @JsonProperty("item_name")
    private final String itemName;

    private final String address;

    private final Order.State state;

    public OrderRO(OrderDto orderDto, Order.State state) {
        this.customer = orderDto.getCustomer();
        this.itemName = orderDto.getItem();
        this.address = orderDto.getAddress();
        this.state = state;
    }
}
