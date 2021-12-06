/**
 * Copyright 2020 VMware, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micrometer.logzio;

import io.micrometer.core.instrument.config.validate.Validated;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LogzioConfigTest {
    private final Map<String, String> props = new HashMap<>();
    private final LogzioConfig config = props::get;

    @Test
    void invalid() {
        assertThat(config.validate().failures().stream().map(Validated.Invalid::getMessage))
                .containsExactly("is required");
    }

    @Test
    void valid() {
        props.put("logzio.token", "secret");
        assertThat(config.validate().isValid()).isTrue();
    }

    @Test
    void uriBasedRegion() {
        props.put("logzio.region", "eu");
        assertThat(config.uri()).isEqualTo("https://listener-eu.logz.io:8053");
    }

    @Test
    void defaultUriWhenMissing() {
        assertThat(config.uri()).isEqualTo("https://listener.logz.io:8053");
    }

}
