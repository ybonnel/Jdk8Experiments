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
package fr.ybonnel;

import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Movies {

    public static void main(String[] args) throws IOException {

        Set<Movie> movies =
                Files.readAllLines(
                        new File(Movies.class.getResource("/movies-mpaa.txt").getPath()).toPath())
                        .stream()
                        .map(Movie::fromLine)
                        .collect(Collectors.toSet());

        Set<Actor> actors = movies.stream().flatMap(movie -> movie.getActors().stream()).collect(Collectors.toSet());

        System.out.println("Movies : " + movies.size());
        System.out.println("Actors : " + actors.size());
        IntSummaryStatistics stats = movies.stream().map(Movie::getYear)
                .map(year -> Integer.parseInt(year))
                .collect(Collectors.summarizingInt(Integer::intValue));

        System.out.println("From " + stats.getMin() + " to " + stats.getMax());


        // Actor with max films.
        chrono("Sequential", () -> actors.stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                (Actor actor) -> movies.stream()
                                        .filter(movie -> movie.getActors().contains(actor))
                                        .count()
                        )
                )
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println));


        chrono("Parallel", () -> actors.stream().parallel()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                (Actor actor) -> movies.stream()
                                        .filter(movie -> movie.getActors().contains(actor))
                                        .count()
                        )
                )
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println));


        chrono("From movies", () -> movies.stream().flatMap(
                movie -> movie.getActors().stream().map(actor -> new Pair<Actor, Movie>(actor, movie))
        ).collect(Collectors.groupingBy(
                Pair<Actor, Movie>::getKey,
                Collectors.counting()
        )).entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println));

        chrono("From movies parallel", () -> movies.parallelStream().flatMap(
                movie -> movie.getActors().stream().map(actor -> new Pair<Actor, Movie>(actor, movie))
        ).collect(Collectors.groupingBy(
                Pair<Actor, Movie>::getKey,
                Collectors.counting()
        )).entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println));


    }

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
