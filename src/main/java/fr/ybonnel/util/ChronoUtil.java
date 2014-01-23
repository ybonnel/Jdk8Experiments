/*
 * Copyright 2013- Yan Bonnel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.ybonnel.util;

import java.util.concurrent.TimeUnit;

public class ChronoUtil {
    public static void chrono(String name, Runnable runnable) {

        System.out.println("Start " + name);

        long startTime = System.nanoTime();

        runnable.run();

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("End of " + name + ", time : " + formatTime(elapsedTime));
    }

    public static String formatTime(long timeInNanoseconds) {

        long minutes = TimeUnit.NANOSECONDS.toMinutes(timeInNanoseconds);
        long secondes = TimeUnit.NANOSECONDS.toSeconds(timeInNanoseconds) -
                TimeUnit.MINUTES.toSeconds(minutes);

        long ms = TimeUnit.NANOSECONDS.toMillis(timeInNanoseconds)
                - TimeUnit.MINUTES.toMillis(minutes)
                - TimeUnit.SECONDS.toMillis(secondes);

        StringBuilder builder = new StringBuilder();

        if (minutes > 0) {
            builder.append(minutes);
            builder.append(" min ");
        }
        if (secondes > 0) {
            builder.append(secondes);
            builder.append(" s ");
        }
        builder.append(ms);
        builder.append(" ms");
        return builder.toString();
    }
}
