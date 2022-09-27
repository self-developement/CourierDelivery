package log.iqpizza6349.springcourierdelivery.batch;

import log.iqpizza6349.springcourierdelivery.domain.order.dao.OrderRepository;
import log.iqpizza6349.springcourierdelivery.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OrderStateBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final OrderRepository orderRepository;

    @Bean
    public Job orderStateJob() throws Exception {
        return jobBuilderFactory.get("orderStateJob")
                .start(orderStateStep())
                .build();
    }

    @Bean
    @JobScope
    public Step orderStateStep() throws Exception {
        return stepBuilderFactory.get("orderPrepareStep")
                .<Order, Order> chunk(10)
                .reader(orderStateReader(null))
                .processor(stateOrderItemProcessor())
                .writer(orderStateWriter(null))
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Order> orderStateReader(
            @Value("#{jobParameters[current]}") Order.State state) {
        Map<String, Object> params = new HashMap<>();
        System.out.println("reader: " + state);
        params.put("state", state);
        return new JpaPagingItemReaderBuilder<Order>()
                .name("orderStateReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("SELECT o from log.iqpizza6349.springcourierdelivery.domain.order.entity.Order o where o.state = :state")
                .parameterValues(params)
                .build();
    }

    @Bean
    public ItemProcessor<Order, Order> stateOrderItemProcessor() {
        return item -> item;
    }

    @Bean
    @StepScope
    public ItemWriter<Order> orderStateWriter(
            @Value("#{jobParameters[status]}") Order.State state) {
        System.out.println("writer: " + state);
        return new OrderStateWriter(orderRepository, state);
    }

    @RequiredArgsConstructor
    static class OrderStateWriter implements ItemWriter<Order> {
        private final OrderRepository orderRepository;
        private final Order.State state;

        @Override
        public void write(List<? extends Order> items) throws Exception {
            for (Order order : items) {
                order.setState(state);
                orderRepository.save(order);
            }
        }
    }

}
