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
import java.util.stream.Stream;

public class Pawn {
    private int x;
    private int y;
    private BigInteger weight;

    public Pawn(int x, int y) {
        this.x = x;
        this.y = y;
        this.weight = BigInteger.ONE;
    }

    public Pawn(int x, int y, BigInteger weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BigInteger getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        Pawn pawn = (Pawn) o;
        return x == pawn.x && y == pawn.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Stream<Pawn> play(int size) {

        if (x < size - 1 && y < size - 1) {
            return Stream.of(new Pawn(x + 1, y, weight),
                    new Pawn(x, y + 1, weight));
        }
        if (x < size - 1) {
            return Stream.of(new Pawn(x + 1, y, weight));
        }
        if (y < size - 1) {
            return Stream.of(new Pawn(x, y + 1, weight));
        }
        return Stream.empty();
    }
}
