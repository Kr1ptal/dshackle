/**
 * Copyright (c) 2023 EmeraldPay, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.emeraldpay.dshackle.monitoring

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Metrics

interface LogMetrics {

    fun produced()
    fun collected()
    fun dropped()

    class Enabled(category: String) : LogMetrics {
        private val produced = Counter.builder("monitoring_logs_produce")
            .description("Log events produced by Dshackle")
            .tag("type", category)
            .register(Metrics.globalRegistry)
        private val collected = Counter.builder("monitoring_logs_collect")
            .description("Log events successfully sent to a storage")
            .tag("type", category)
            .register(Metrics.globalRegistry)
        private val dropped = Counter.builder("monitoring_logs_drop")
            .description("Log events dropped w/o sending")
            .tag("type", category)
            .register(Metrics.globalRegistry)

        override fun produced() {
            produced.increment()
        }

        override fun collected() {
            collected.increment()
        }

        override fun dropped() {
            dropped.increment()
        }
    }

    class None : LogMetrics {

        override fun produced() {
        }

        override fun collected() {
        }

        override fun dropped() {
        }
    }
}
