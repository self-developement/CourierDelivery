package log.iqpizza6349.springcourierdelivery.batch;

import log.iqpizza6349.springcourierdelivery.domain.item.dao.ItemRepository;
import log.iqpizza6349.springcourierdelivery.domain.item.entity.Item;
import log.iqpizza6349.springcourierdelivery.domain.order.dao.OrderRepository;
import log.iqpizza6349.springcourierdelivery.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ItemBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Bean
    public Job itemStorageJob() throws Exception {
        return jobBuilderFactory.get("itemStorageJob")
                .start(storageStep())
                .next(orderPrepareStep())
                .build();
    }

    @Bean
    @JobScope
    public Step storageStep() throws Exception {
        return stepBuilderFactory.get("storageStep")
                .< Item, Item > chunk(10)
                .reader(jpaPagingItemReader())
                .processor(itemItemProcessor())
                .writer(itemJpaItemWriter())
                .build();
    }

    @Bean
    @JobScope
    public Step orderPrepareStep() throws Exception {
        return stepBuilderFactory.get("orderPrepareStep")
                .<Order, Order> chunk(10)
                .reader(orderJpaPagingItemReader())
                .processor(orderItemProcessor())
                .writer(orderItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Item> jpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Item>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("SELECT i FROM Item i WHERE i.amount = 0")
                .build();
    }

    @Bean
    public JpaPagingItemReader<Order> orderJpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Order>()
                .name("orderJpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("SELECT o From log.iqpizza6349.springcourierdelivery.domain.order.entity.Order o WHERE o.state = 'NO_STOCK'")
                .build();
    }

    @Bean
    public ItemProcessor<Item, Item> itemItemProcessor() {
        return item -> item;
    }

    @Bean
    public ItemProcessor<Order, Order> orderItemProcessor() {
        return item -> item;
    }

    @Bean
    public ItemWriter<Item> itemJpaItemWriter() {
        return new ItemDBWriter(itemRepository);
    }

    @Bean
    public ItemWriter<Order> orderItemWriter() {
        return new OrderDBWriter(orderRepository);
    }

    @RequiredArgsConstructor
    static class ItemDBWriter implements ItemWriter<Item> {

        private final ItemRepository itemRepository;

        @Override
        public void write(List<? extends Item> items) throws Exception {
            for (Item item : items) {
                item.setAmount(10);
                itemRepository.save(item);
            }
        }
    }

    @RequiredArgsConstructor
    static class OrderDBWriter implements ItemWriter<Order> {
        private final OrderRepository orderRepository;

        @Override
        public void write(List<? extends Order> items) throws Exception {
            for (Order order : items) {
                order.setState(Order.State.PRODUCT_PREPARE);
                orderRepository.save(order);
            }
        }
    }
}
