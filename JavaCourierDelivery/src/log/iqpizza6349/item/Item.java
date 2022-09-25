package log.iqpizza6349.item;

public class Item {

    // 상품 식별키
    private final Long id;

    // 삼품명
    private final String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Item(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
