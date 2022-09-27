package log.iqpizza6349.springcourierdelivery.domain.item.service;

import log.iqpizza6349.springcourierdelivery.domain.item.dao.ItemRepository;
import log.iqpizza6349.springcourierdelivery.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item findByName(String itemName) {
        return itemRepository.findByName(itemName)
                .orElseGet(() -> saveItemByName(itemName));
    }

    protected Item saveItemByName(String itemName) {
        return itemRepository.save(
                Item.builder()
                .name(itemName)
                .amount(0)
        .build());
    }

    @Transactional(readOnly = true)
    public Long itemCount(String itemName) {
        Long count = itemRepository.findAmountByName(itemName);
        return (count == null) ? 0 : count;
    }
}
