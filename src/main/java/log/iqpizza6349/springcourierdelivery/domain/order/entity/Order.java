package log.iqpizza6349.springcourierdelivery.domain.order.entity;

import log.iqpizza6349.springcourierdelivery.domain.item.entity.Item;
import log.iqpizza6349.springcourierdelivery.exception.GlobalException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Entity(name = "orders")
@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ordered;

    @OneToOne
    private Item item;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public void cancel() {
        if (state != State.PRODUCT_PREPARE && state != State.NO_STOCK) {
            throw new GlobalException(HttpStatus.CONFLICT, "배송 준비를 마쳤거나, 배송 중입니다.");
        }

        state = State.CANCELED;
    }

    public void confirmation() {
        if (state != State.DELIVERY_COMPLETE) {
            throw new GlobalException(HttpStatus.CONFLICT, "배송 물품이 아직 도착하지 않았습니다.");
        }

        state = State.SHIPPING_CONFIRMATION;
    }

    enum State {
        PRODUCT_PREPARE, NO_STOCK,
        DELIVERY_PREPARE, SHIPPING, CANCELED,
        DELIVERY_COMPLETE, SHIPPING_CONFIRMATION
    }
}
