package log.iqpizza6349.springcourierdelivery.domain.order.service;

import log.iqpizza6349.springcourierdelivery.domain.item.entity.Item;
import log.iqpizza6349.springcourierdelivery.domain.item.service.ItemService;
import log.iqpizza6349.springcourierdelivery.domain.order.dao.OrderRepository;
import log.iqpizza6349.springcourierdelivery.domain.order.dto.OrderDto;
import log.iqpizza6349.springcourierdelivery.domain.order.entity.Order;
import log.iqpizza6349.springcourierdelivery.domain.order.ro.OrderRO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;

    public OrderRO orderRegistration(final OrderDto orderDto) {
        String itemName = orderDto.getItem();
        log.info(itemName);
        long countOfItem = itemService.itemCount(itemName);
        Order.State state = Order.State.PRODUCT_PREPARE;
        if (countOfItem == 0) {
            state = Order.State.NO_STOCK;
        }

        Item item = itemService.findByName(itemName);
        Order.State finalState = state;
        Runnable runnable = () -> orderRepository.save(orderDto.toEntity(item, finalState));
        runnable.run();
        return new OrderRO(orderDto, state);
    }
}
