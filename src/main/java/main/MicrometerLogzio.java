package main;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.lang.Nullable;
//import io.micrometer.elastic.ElasticConfig;
//import io.micrometer.elastic.ElasticMeterRegistry;
import io.micrometer.logzio.LogzioConfig;
import io.micrometer.logzio.LogzioMeterRegistry;
//import io.micrometer.prometheus.PrometheusConfig;
//import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MicrometerLogzio {
    public static void main(String[] args) {

        LogzioConfig logzioConfig = new LogzioConfig() {

            @Override
            @Nullable
            public String get(String k) {
                return null;
            }

            @Override
            public String host() {
                return "http://listener.logz.io:8070";
            }

            @Override
            public String token() {
                return "DYGrIUUSMPeUwahTGhZybBkwqKOAglBz";
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(30);
            }

        };

        MeterRegistry registry = new LogzioMeterRegistry(logzioConfig, Clock.SYSTEM);
        //MeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

//        ElasticConfig elasticConfig = new ElasticConfig() {
//            @Override
//            @Nullable
//            public String get(String k) {
//                return null;
//            }
//
//            @Override
//            public Duration step() {
//                return Duration.ofSeconds(10);
//            }
//
//        };
//
//        MeterRegistry registry = new ElasticMeterRegistry(elasticConfig, Clock.SYSTEM);




        List<String> tagsList = Arrays.asList("gauge-d1", "gauge-dimension1");
        Counter counter = registry.counter("my-counter-metric", "micrometer_d1","dimension1","micrometer_d2","dimension2");

        List gauge = registry.gauge("listGauge", Collections.emptyList(),tagsList , List::size);


        while(counter.count() < 5){
            counter.increment();
            //tagsList.add("ff");
            try {
                Thread.sleep(10000);
            }catch(InterruptedException e){
                System.out.println(e.toString());
            }
        }
    }

}
