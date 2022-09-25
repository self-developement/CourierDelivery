package log.iqpizza6349;

import log.iqpizza6349.item.Item;
import log.iqpizza6349.item.ItemDAO;
import log.iqpizza6349.order.Order;
import log.iqpizza6349.order.OrderDAO;
import log.iqpizza6349.order.OrderService;
import log.iqpizza6349.order.OrderState;

public class Test {

    private final ItemDAO itemDAO = new ItemDAO();
    private final OrderService orderService = new OrderService();
    private final OrderDAO orderDAO = new OrderDAO();

    private Order order() {
        return new Order(null, "customer", "item", "address");
    }

    private Order order(OrderState orderState) {
        return new Order(null, "customer", "item", "address", orderState);
    }

    // 상품 주문 - 최초 상태는 상품 준비 중이다.
    public void orderWhenStockIsEnough() {
        // given
        itemDAO.save(new Item(null, "item1"));
        String customer = "customer1";
        String item = "item1";
        String address = "address1";

        // when
        Order order = orderService.orderRegistration(customer, item, address);

        // then
        isNotNull(order);
        isEqual(order.getOrdered(), customer);
        isEqual(order.getItem(), item);
        isEqual(order.getAddress(), address);
        isEqual(order.getOrderState(), OrderState.PRODUCT_PREPARE);
    }

    // 상품 주문 - 재고가 부족할 때
    public void orderWhenStockIsNotEnough() {
        String customer = "customer";
        String item = "item";
        String address = "address";

        // when
        Order order = orderService.orderRegistration(customer, item, address);

        // then
        isNotNull(order);
        isEqual(order.getOrdered(), customer);
        isEqual(order.getItem(), item);
        isEqual(order.getAddress(), address);
        isEqual(order.getOrderState(), OrderState.NO_STOCK);
    }

    // 배송 준비 중
    public void prepareToShip() {
        Order order = orderDAO.save(order());

        order.setOrderState(OrderState.DELIVERY_PREPARE);

        isEqual(order.getOrderState() == OrderState.DELIVERY_PREPARE, true);
    }

    public void shipping() {
        Order order = orderDAO.save(order());

        order.setOrderState(OrderState.SHIPPING);

        isEqual(order.getOrderState() == OrderState.SHIPPING, true);
    }

    public void cancelTest() {
        Order order = orderDAO.save(order());

        order.cancel();

        isEqual(order.getOrderState() == OrderState.CANCELED, true);
    }

    public void shippingCancel() throws Exception {
        Order order = orderDAO.save(order(OrderState.SHIPPING));

        try {
            order.cancel();         // might be exception
        } catch (RuntimeException e) {
            throw new Exception(e);
        }
    }

    public void confirmationTest() {
        Order order = orderDAO.save(order(OrderState.DELIVERY_COMPLETE));

        order.confirmation();
    }

    public void confirmationWhenNotArrived() throws Exception {
        Order order = orderDAO.save(order(OrderState.SHIPPING));

        try {
            order.confirmation();   // might be exception
        } catch (RuntimeException e) {
            throw new Exception(e);
        }
    }

    public void isEqual(Object a, Object b) {
        if (!a.equals(b)) throw new RuntimeException();
    }

    public void isNotNull(Object a) {
        if (a == null) throw new RuntimeException();
    }

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.orderWhenStockIsEnough();
        test.orderWhenStockIsNotEnough();
        test.prepareToShip();
        test.shipping();
        test.cancelTest();
        test.shippingCancel();
        test.confirmationTest();
        test.confirmationWhenNotArrived();
    }
}
