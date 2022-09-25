package log.iqpizza6349.item;

import java.util.HashMap;
import java.util.Map;

public class ItemDAO {

    private static final Map<Long, Item> ITEMS = new HashMap<>();

    public Item save(Item item) {
        long id = (item.getId() == null) ? 0 : item.getId();
        ITEMS.put(id, item);
        return ITEMS.get(id);
    }

    public int countByName(String name) {
        return (int) ITEMS.values().stream()
                .filter(item -> item.getName().equals(name)).count();
    }
}
