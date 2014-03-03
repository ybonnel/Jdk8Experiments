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
package fr.ybonnel.setlistarray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static fr.ybonnel.util.ChronoUtil.chrono;

public class SetListArray {

    public static void main(String[] args) {

        // Create a set of 1M integers.
        Set<Integer> set = new Random().ints(1_000_000).mapToObj(Integer::new).collect(Collectors.toSet());
        // Create a list from the set.
        List<Integer> list = new ArrayList<>(set);
        // Create an array of Integer from the set.
        Integer[] array = set.toArray(new Integer[set.size()]);
        // Create an array of int from the set.
        int[] primitiveArray = set.stream().mapToInt(Integer::intValue).toArray();

        chrono("set", 100, () -> set.stream().max(Integer::compareTo).get());
        chrono("set parallel", 100, () -> set.parallelStream().max(Integer::compareTo).get());
        chrono("list", 100, () -> list.stream().max(Integer::compareTo).get());
        chrono("list parallel", 100, () -> list.parallelStream().max(Integer::compareTo).get());
        chrono("array", 100, () -> Arrays.stream(array).max(Integer::compareTo).get());
        chrono("array parallel", 100, () -> Arrays.stream(array).parallel().max(Integer::compareTo).get());
        chrono("primitiveArray", 100, () -> Arrays.stream(primitiveArray).max().getAsInt());
        chrono("primitiveArray parallel", 100, () -> Arrays.stream(primitiveArray).parallel().max().getAsInt());
    }


}
