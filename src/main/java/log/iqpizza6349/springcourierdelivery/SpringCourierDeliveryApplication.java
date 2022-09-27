package log.iqpizza6349.springcourierdelivery;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableBatchProcessing
public class SpringCourierDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCourierDeliveryApplication.class, args);
	}

}
