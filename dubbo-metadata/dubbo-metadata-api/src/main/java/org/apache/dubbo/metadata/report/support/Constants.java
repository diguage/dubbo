/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.metadata.report.support;

public interface Constants {
    String METADATA_REPORT_KEY = "metadata";

    Integer DEFAULT_METADATA_REPORT_RETRY_TIMES = 100;

    Integer DEFAULT_METADATA_REPORT_RETRY_PERIOD = 3000;

    Boolean DEFAULT_METADATA_REPORT_CYCLE_REPORT = true;

    String CACHE = ".cache";

    String DUBBO_METADATA = "/.dubbo/dubbo-metadata-";
}
