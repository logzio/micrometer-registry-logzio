### Logz.io Micrometer registry
A [Micrometer metrics](https://micrometer.io/) registry for sending dimensional metrics to Logz.io.

This registry should be able to be used any application that uses micrometer for recording metrics.

### Usage:

1) via gradle: 
  
    `implementation 'io.logz:micrometer-registry-logzio:1.0-SNAPSHOT'`

2) via maven:

```
    <dependency>
        <groupId>io.logz</groupId>
        <artifactId>micrometer-registry-logzio</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```

3) The registry class is `com.newrelic.telemetry.micrometer.NewRelicRegistry`. 
It will need to be built via a `com.newrelic.telemetry.micrometer.NewRelicRegistryConfig` implementation. An example of how you might do that is in the wiki: [Spring-Config-Example](https://github.com/newrelic/micrometer-registry-newrelic/wiki/Spring-Config-Example)

In order to send metrics to New Logz, you will also need an Insert API Key. Please see [New Relic Api Keys](https://docs.newrelic.com/docs/apis/get-started/intro-apis/types-new-relic-api-keys#) for more information.

And, that's it! For details on what your metrics will look like once they are reported, please see the [Micrometer exporter spec](https://github.com/newrelic/exporter-specs). 

### Javadoc for this project can be found here: [![Javadocs][javadoc-image]][javadoc-url]

