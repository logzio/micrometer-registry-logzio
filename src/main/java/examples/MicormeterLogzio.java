package examples;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.lang.Nullable;
import io.micrometer.logzio.LogzioConfig;
import io.micrometer.logzio.LogzioMeterRegistry;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
class ValueProvidor {
    public int get_value(){
        return 10;
    }
}
class MicrometerLogzio {

    public static void main(String[] args) {
        LogzioConfig logzioConfig = new LogzioConfig() {
            @Override
            @Nullable
            public String get(String k) {
                return null;
            }
            @Override
            public String region() {
                return "us";
            }
            @Override
            public String uri() {
                return "https://listener.logz.io:8053";
            }
            @Override
            public String token() {
                return "IsIUMNTOTlSxltfCCyeaShJarQqHIKlE";
            }
            @Override
            public Duration step() {
                return Duration.ofSeconds(30);
            }
        };
        // Initialize registry
        LogzioMeterRegistry registry = new LogzioMeterRegistry(logzioConfig, Clock.SYSTEM);
        // Define tags (labels)
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("env","dev"));

        // Create counter
        Counter counter = Counter
                .builder("counter_example")
                .description("a description of what this counter does") // optional
                .tags(tags) // optional
                .register(registry);
        // Increment your counter
        counter.increment();
        counter.increment(2);

        // Create DistributionSummary
        DistributionSummary summary = DistributionSummary
                .builder("summary_example")
                .description("a description of what this summary does") // optional
                .tags(tags) // optional
                .register(registry);
        // Record values to distributionSummary
        summary.record(10);
        summary.record(20);
        summary.record(30);

        // Create Gauge
        List<String> cache = new ArrayList<>();
        // Track list size
        Gauge gauge = Gauge
                .builder("cache_size_gauge_example", cache, List::size)
                .tags(tags)
                .register(registry);
        cache.add("1");
        // Track map size
        Map<String, Integer> map_gauge = registry.gaugeMapSize("mapGauge", tags, new HashMap<>());
        map_gauge.put("key",1);
        // set value manually
        AtomicInteger manual_gauge = registry.gauge("manual_gauge_example", new AtomicInteger(0));
        manual_gauge.set(83);


        // Create Timer
        Timer timer = Timer
                .builder("timer_example")
                .description("a description of what this timer does") // optional
                .tags(tags) // optional
                .register(registry);
        // You can set a value manually
        timer.record(1500,TimeUnit.MILLISECONDS);
        // You can record the timing of a function
        timer.record(()-> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}