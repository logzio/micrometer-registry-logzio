package io.micrometer.logzio;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.micrometer.core.ipc.http.HttpUrlConnectionSender;
import io.micrometer.core.ipc.http.HttpSender.Response;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RetryHttpClient extends HttpUrlConnectionSender {
    
    private final RetryConfig retryConfig = RetryConfig.custom()
            .maxAttempts(2)
            .waitDuration(Duration.ofSeconds(5))
            .retryOnResult((response) -> {
                Integer[] RetryStatuses = new Integer[]{408, 500, 502, 503, 504, 522, 524};
                List<Integer> RetryStatusesList = new ArrayList<>(Arrays.asList(RetryStatuses));
                Response r = (Response) response;
                boolean retry = RetryStatusesList.contains(r.code());
                if (retry)
                    System.out.println(String.format("Got status code: %d Retrying in 2 seconds",r.code()));
                return RetryStatusesList.contains(r.code());
            })
            .build();

    private final Retry retry = Retry.of("logzio-metrics", this.retryConfig);

    public RetryHttpClient(Duration connectTimeout, Duration readTimeout) {
        super(connectTimeout,readTimeout);
    }

    @Override
    public Response send(Request request) {
        CheckedFunction0<Response> retryableSupplier = Retry.decorateCheckedSupplier(
                this.retry,
                () -> super.send(request));
        return Try.of(retryableSupplier).get();
    }

}
