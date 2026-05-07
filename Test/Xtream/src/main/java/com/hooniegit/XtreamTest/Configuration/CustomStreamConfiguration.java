package com.hooniegit.XtreamTest.Configuration;

import java.util.List;

import com.hooniegit.Xtream.Tools.Handler;
import com.hooniegit.Xtream.Tools.StreamAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hooniegit.XtreamTest.Configuration.DataClass.Sample;
import com.hooniegit.XtreamTest.Configuration.Handlers.Handler01;
import com.hooniegit.XtreamTest.Configuration.Handlers.Handler02;
import org.springframework.context.annotation.Import;

@Configuration
@Import(StreamAutoConfiguration.class)
public class CustomStreamConfiguration {

    @Bean
    public List<Handler<Sample>> handlers() {
        return List.of(new Handler01(), new Handler02());
    }

    @Bean
    public StreamAutoConfiguration<Sample> streamAutoConfiguration() {
        return new StreamAutoConfiguration<>();
    }

}

