package log.iqpizza6349.order;

/**
 * 주문한 고객이 누구인지
 * 주문한 물품이 무엇인지
 * 어디로 배송해야하는 지
 * 현재 주문상태는 어떠한지
 */
public class Order {

    // 주문 식별키
    private final Long id;

    private final String ordered;

    private final String item;

    private final String address;

    private OrderState orderState;

    public Long getId() {
        return id;
    }

    public String getOrdered() {
        return ordered;
    }

    public String getItem() {
        return item;
    }

    public String getAddress() {
        return address;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public Order(Long id, String ordered, String item, String address) {
        this(id, ordered, item, address, OrderState.PRODUCT_PREPARE);
    }

    public Order(Long id, String ordered, String item, String address, OrderState orderState) {
        this.id = id;
        this.ordered = ordered;
        this.item = item;
        this.address = address;
        this.orderState = orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void cancel() {
        if (orderState != OrderState.PRODUCT_PREPARE && orderState != OrderState.NO_STOCK) {
            throw new RuntimeException("배송 준비를 마쳤거나 배송 중 입니다.");
        }

        this.orderState = OrderState.CANCELED;
    }

    public void confirmation() {
        if (orderState != OrderState.DELIVERY_COMPLETE) {
            throw new RuntimeException("배송 물품이 아직 도착하지 않았습니다.");
        }

        this.orderState = OrderState.SHIPPING_CONFIRMATION;
    }
}
