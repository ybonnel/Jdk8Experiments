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
package fr.ybonnel.arrays;

import java.util.Arrays;
import java.util.Random;

import static fr.ybonnel.util.ChronoUtil.chrono;

public class ArrayTest {

    public static int[] generateBigArrayOfRandomnInt(int size) {
        return new Random().ints(size).toArray();
    }

    public static int maxOfArrayJdk7(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public static int maxOfArrayJdk8(int[] array) {
        return Arrays.stream(array).max().getAsInt();
    }

    public static int maxOfArrayParallelJdk8(int[] array) {
        return Arrays.stream(array).parallel().max().getAsInt();
    }

    public static void main(String[] args) {

        int[] array = generateBigArrayOfRandomnInt(100_000_000);

        chrono("maxOfArrayJdk7", 100, () -> maxOfArrayJdk7(array));
        chrono("maxOfArrayJdk8", 100, () -> maxOfArrayJdk8(array));
        chrono("maxOfArrayParallelJdk8", 100, () -> maxOfArrayParallelJdk8(array));

    }

}
