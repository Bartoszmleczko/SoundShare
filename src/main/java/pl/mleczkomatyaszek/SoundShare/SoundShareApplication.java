package pl.mleczkomatyaszek.SoundShare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
@SpringBootApplication
public class SoundShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoundShareApplication.class, args);
	}


}
