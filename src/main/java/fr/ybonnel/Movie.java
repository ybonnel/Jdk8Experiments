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

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Movie {

    private String title;
    private String year;

    private Set<Actor> actors;

    public Movie(String title, String year) {
        this.title = title;
        this.year = year;
    }

    public static Movie fromLine(String line) {
        List<String> columns = Arrays.asList(line.split("/"));

        String title = columns.get(0).substring(0, columns.get(0).lastIndexOf("(") - 1);
        String year = columns.get(0).substring(columns.get(0).lastIndexOf("(") + 1, columns.get(0).lastIndexOf(")"));
        if (year.contains(",")) {
            year = year.split(",")[0];
        }
        Movie movie = new Movie(title, year);

        movie.actors = columns.subList(1, columns.size()).stream()
                .map(actor -> {
                    String lastName = actor.split(",")[0];
                    String firstName = actor.split(",").length > 1 ? actor.split(",")[1].substring(1) : "unkown";
                    return new Actor(firstName, lastName);
                })
                .collect(Collectors.toSet());

        return movie;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return title.equals(movie.title) && year.equals(movie.year);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + year.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
