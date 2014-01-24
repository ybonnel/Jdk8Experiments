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
package fr.ybonnel.fibonacci;

import java.math.BigInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static fr.ybonnel.util.ChronoUtil.chrono;

public class Fibonacci {

    public static class FibonacciGenerator implements Supplier<BigInteger> {

        private BigInteger previous = BigInteger.ZERO;
        private BigInteger current = BigInteger.ONE;

        @Override
        public synchronized BigInteger get() {
            BigInteger next = current.add(previous);
            previous = current;
            current = next;
            return previous;
        }
    }

    public static BigInteger findNthFibonacci(long n) {
         return Stream.generate(new FibonacciGenerator()).limit(n).max(BigInteger::compareTo).get();
    }

    public static BigInteger findNthFibonacciOld(long n) {
        BigInteger previous = BigInteger.ZERO;
        BigInteger current = BigInteger.ONE;
        for (long index = 1; index < n; index++) {
            BigInteger next = current.add(previous);
            previous = current;
            current = next;
        }

        return current;
    }

    public static void main(String[] args) {
        chrono("Fibonacci(1_000_000) jdk8", () -> findNthFibonacci(1_000_000));
        chrono("Fibonacci(1_000_000) jdk7", () -> findNthFibonacciOld(1_000_000));
    }
}
