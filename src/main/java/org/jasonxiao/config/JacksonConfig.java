package org.jasonxiao.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jason Xiao
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Module hibernate5Module() {
        return new Hibernate5Module();
    }
}
