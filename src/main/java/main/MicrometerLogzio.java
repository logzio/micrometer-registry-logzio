package main;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.lang.Nullable;
//import io.micrometer.elastic.ElasticConfig;
//import io.micrometer.elastic.ElasticMeterRegistry;
import io.micrometer.logzio.LogzioConfig;
import io.micrometer.logzio.LogzioMeterRegistry;

import java.time.Duration;

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



        Counter counter = registry.counter("0", "micrometer_d1","dimension1","micrometer_d2","dimension2");
        Counter counter2 = registry.counter("my-new-counter-2", "micrometer_d1","dimension1","micrometer_d2","dimension2");

        while(counter.count() < 5){
            counter.increment();
            counter2.increment(2);
            try {
                Thread.sleep(10000);
            }catch(InterruptedException e){
                System.out.println(e.toString());
            }
        }
    }

}
