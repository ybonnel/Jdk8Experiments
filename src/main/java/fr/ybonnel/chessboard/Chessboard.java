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
package fr.ybonnel.chessboard;


import java.math.BigInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.ybonnel.util.ChronoUtil.chrono;

public class Chessboard {


    /**
     * Is a chessboard of n * m cells and a pawn placed at the bottom left,
     * how many are there possible route to reach the square at the top right moving only along the horizontal and vertical axes?
     * Rules:
     * A pawn can only move up or right.
     */
    public static BigInteger calculate(int size) {
        Stream<Pawn> allPawns = Stream.of(new Pawn(0, 0));
        int nbMoves = size * 2 - 2;
        for (int index = 1; index <= nbMoves; index++) {

            allPawns = allPawns.flatMap(pion -> pion.play(size)).collect(
                    Collectors.groupingBy(
                            Function.<Pawn>identity(),
                            Collectors.toList()
                    )
            ).entrySet().stream().map(entry ->
                    new Pawn(entry.getKey().getX(), entry.getKey().getY(),
                            entry.getValue().stream().map(Pawn::getWeight)
                                    .reduce(BigInteger.ZERO, BigInteger::add)));

        }
        return allPawns.map(Pawn::getWeight).reduce(BigInteger.ZERO, BigInteger::add);
    }

    public static BigInteger calculateParallel(int size) {
        Stream<Pawn> allPawns = Stream.of(new Pawn(0, 0)).parallel();
        int nbMoves = size * 2 - 2;
        for (int index = 1; index <= nbMoves; index++) {

            allPawns = allPawns.flatMap(pion -> pion.play(size)).collect(
                    Collectors.groupingBy(
                            Function.<Pawn>identity(),
                            Collectors.toList()
                    )
            ).entrySet().parallelStream().map(entry ->
                    new Pawn(entry.getKey().getX(), entry.getKey().getY(),
                            entry.getValue().stream().map(Pawn::getWeight)
                                    .reduce(BigInteger.ZERO, BigInteger::add)));

        }
        return allPawns.map(Pawn::getWeight).reduce(BigInteger.ZERO, BigInteger::add);
    }

    public static void main(String[] args) {

        chrono("Chessboard 1000 - sequential", () -> System.out.println(calculate(1000)));
        chrono("Chessboard 1000 - parallel", () -> System.out.println(calculateParallel(1000)));
        chrono("Chessboard 10000 - sequential", () -> System.out.println(calculate(10000)));
        chrono("Chessboard 10000 - parallel", () -> System.out.println(calculateParallel(10000)));
    }



}
