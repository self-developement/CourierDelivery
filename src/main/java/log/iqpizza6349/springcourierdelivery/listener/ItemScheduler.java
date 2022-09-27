package log.iqpizza6349.springcourierdelivery.listener;

import log.iqpizza6349.springcourierdelivery.batch.ItemBatch;
import log.iqpizza6349.springcourierdelivery.batch.OrderStateBatch;
import log.iqpizza6349.springcourierdelivery.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemScheduler {

    private final JobLauncher jobLauncher;
    private final ItemBatch itemBatch;
    private final OrderStateBatch orderStateBatch;

    @Scheduled(fixedDelay = 60_000)     // 60 초 마다 재고가 들어옴
    public void itemStorage() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParameterMap);
        try {
            JobExecution jobExecution = jobLauncher.run(itemBatch.itemStorageJob(), jobParameters);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static int index = 0;

    @Scheduled(fixedDelay = 1000)
    public void orderStateChange() {
        Order.State[] states = Order.State.values();
        if (index == states.length - 2) {
            index = 0;
        }

        JobParameters parameters = new JobParametersBuilder()
                .addString("current", states[index++].name())
                .addString("status", states[index].name())
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            JobExecution jobExecution = jobLauncher.run(orderStateBatch.orderStateJob(), parameters);

            log.info("Job Execution: " + jobExecution.getStatus());
            log.info("Job getJobConfigurationName: " + jobExecution.getJobConfigurationName());
            log.info("Job getJobId: " + jobExecution.getJobId());
            log.info("Job getExitStatus: " + jobExecution.getExitStatus());
            log.info("Job getJobInstance: " + jobExecution.getJobInstance());
            log.info("Job getStepExecutions: " + jobExecution.getStepExecutions());
            log.info("Job getLastUpdated: " + jobExecution.getLastUpdated());
            log.info("Job getFailureExceptions: " + jobExecution.getFailureExceptions());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
