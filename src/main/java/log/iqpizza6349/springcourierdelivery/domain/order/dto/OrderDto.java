package log.iqpizza6349.springcourierdelivery.domain.order.dto;

import log.iqpizza6349.springcourierdelivery.domain.item.entity.Item;
import log.iqpizza6349.springcourierdelivery.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class OrderDto {

    @NotBlank
    private String customer;

    @NotBlank
    private String item;

    @NotBlank
    private String address;

    public Order toEntity(Item item, Order.State state) {
        return Order.builder()
                .ordered(customer)
                .item(item)
                .address(address)
                .state(state)
                .build();
    }
}
