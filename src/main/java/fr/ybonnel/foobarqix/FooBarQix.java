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
package fr.ybonnel.foobarqix;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This is a solution for FooBarQix : http://www.code-story.net/blog/posts/s01e01
 */
public class FooBarQix {

    private static Map<Integer, String> mapOfReplaceInt = new HashMap<>();
    private static Map<Character, String> mapOfReplaceChar = new HashMap<>();

    static {
        mapOfReplaceInt.put(3, "Foo");
        mapOfReplaceInt.put(5, "Bar");
        mapOfReplaceInt.put(7, "Qix");
        mapOfReplaceInt.entrySet().forEach(entry ->
                mapOfReplaceChar.put(Integer.toString(entry.getKey()).charAt(0), entry.getValue())
        );
    }

    private static class FooBarQixGenerator {

        long count = 0;

        public String generate() {
            count++;
            final StringBuilder result = new StringBuilder();

            mapOfReplaceInt.entrySet().stream().filter(entry -> count % entry.getKey() == 0).map(Map.Entry::getValue).forEach(result::append);

            Long.toString(count).chars().mapToObj(n -> mapOfReplaceChar.getOrDefault((char)n, "")).forEach(result::append);

            if (result.length() != 0) {
                return result.toString();
            }

            return Long.toString(count);
        }

    }

    public static void main(String[] args) {
        FooBarQixGenerator generator = new FooBarQixGenerator();
        Stream.generate(generator::generate).limit(100).forEach(System.out::println);
    }

}
