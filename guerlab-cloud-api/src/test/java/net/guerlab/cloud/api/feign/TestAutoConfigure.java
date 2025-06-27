package net.guerlab.cloud.api.feign;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class TestAutoConfigure {

	@Bean
	public IntegerHttpMessageConverter integerHttpMessageConverter() {
		return new IntegerHttpMessageConverter();
	}

	@Bean
	public PdfHttpMessageConverter pdfHttpMessageConverter() {
		return new PdfHttpMessageConverter();
	}
}
