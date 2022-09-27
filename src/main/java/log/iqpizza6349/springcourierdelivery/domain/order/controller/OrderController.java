package log.iqpizza6349.springcourierdelivery.domain.order.controller;

import log.iqpizza6349.springcourierdelivery.domain.order.dto.OrderDto;
import log.iqpizza6349.springcourierdelivery.domain.order.ro.OrderRO;
import log.iqpizza6349.springcourierdelivery.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderRO orderRegistration(@RequestBody @Valid OrderDto orderDto) {
        return orderService.orderRegistration(orderDto);
    }
}
