package log.iqpizza6349.order;

import log.iqpizza6349.item.ItemDAO;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final ItemDAO itemDAO = new ItemDAO();

    public Order orderRegistration(String customer, String item, String address) {
        int countOfItem = itemDAO.countByName(item);
        if (countOfItem == 0) {
            requiredStock(); // 실제 서비스에서는 스케쥴러를 사용하거나, webflux 를 사용하여 적용
            return orderDAO.save(new Order(1L, customer, item, address, OrderState.NO_STOCK));
        }

        return orderDAO.save(new Order(1L, customer, item, address));
    }

    private void requiredStock() {
        try {
            System.out.println("재고가 없어서 기다리는 중");
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }
}
