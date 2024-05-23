package cl.panaderia.productos.config;

import cl.panaderia.productos.dominio.TransBank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransBankConfig {

    @Bean
    @ConfigurationProperties("transbank")
    public TransBank transBank() {
        TransBank transBank = new TransBank();
        transBank.setApiKey("API_KEY");
        transBank.setCommerceCode("COMMERCE_CODE");
        transBank.setEnvironment("ENVIRONMENT");
        transBank.setReturnUrl("RETURN_URL");
        return transBank;
    }
}
