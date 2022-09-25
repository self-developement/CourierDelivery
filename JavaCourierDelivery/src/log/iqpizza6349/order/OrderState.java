package log.iqpizza6349.order;

/**
 * 상품 준비 중 - 재고가 있고, 곧 배송할 예정
 * 상품 재고 없음 - 재고가 없어, 재고를 기다리는 중
 * 배송 준비 중 - 배송할 예정, 더 이상 주문 취소 불가
 * 배송 중 - 배송하고 있는 상태, 주문 취소 불가
 * 배송 완료 - 배송이 도착한 상태, 주문 취소 불가
 * 배송 확인 - 고객이 배송이 도착한 것을 확인한 상태
 * 취소됨 - 고객이 물품을 취소한 경우
 */
public enum OrderState {
    PRODUCT_PREPARE, NO_STOCK,
    DELIVERY_PREPARE, SHIPPING, CANCELED,
    DELIVERY_COMPLETE, SHIPPING_CONFIRMATION
}
