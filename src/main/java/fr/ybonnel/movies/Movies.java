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
package fr.ybonnel.movies;

import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static fr.ybonnel.util.ChronoUtil.chrono;

public class Movies {

    private static Set<Movie> movies;
    private static Set<Actor> actors;

    /**
     * Load dataset into movies and actors.
     * http://introcs.cs.princeton.edu/java/data/movies-mpaa.txt
     */
    private static void loadDatas() throws IOException {
        movies = Files.readAllLines(
                new File(Movies.class.getResource("/movies-mpaa.txt").getPath()).toPath())
                .stream()
                .map(Movie::fromLine)
                .collect(Collectors.toSet());
        actors = movies.stream().flatMap(movie -> movie.getActors().stream()).collect(Collectors.toSet());

        System.out.println("Movies : " + movies.size());
        System.out.println("Actors : " + actors.size());
        IntSummaryStatistics stats = movies.stream().map(Movie::getYear)
                .map(Integer::parseInt)
                .collect(Collectors.summarizingInt(Integer::intValue));

        System.out.println("From " + stats.getMin() + " to " + stats.getMax());
    }

    public static void main(String[] args) throws IOException {
        loadDatas();
        actorWithMaxFilm();
    }

    /**
     * Find the actor with max movies.
     */
    private static void actorWithMaxFilm() {
        // First method :
        //  start from actors, for each actor count films and find max.
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

        // First method with parallel.
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

        // Second method :
        //  start from movies,
        //  flatmap into pairs of Actor, Movie
        //  groupby actors
        //  max by counting.
        chrono("From movies", () -> movies.stream().flatMap(
                movie -> movie.getActors().stream().map(actor -> new Pair<>(actor, movie))
        ).collect(Collectors.groupingBy(
                Pair<Actor, Movie>::getKey,
                Collectors.counting()
        )).entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println));

        chrono("From movies parallel", () -> movies.parallelStream().flatMap(
                movie -> movie.getActors().stream().map(actor -> new Pair<>(actor, movie))
        ).collect(Collectors.groupingBy(
                Pair<Actor, Movie>::getKey,
                Collectors.counting()
        )).entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println));
    }

}
