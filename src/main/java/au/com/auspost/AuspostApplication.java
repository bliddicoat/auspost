package au.com.auspost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "au.com.auspost")
public class AuspostApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuspostApplication.class, args);
	}

}
