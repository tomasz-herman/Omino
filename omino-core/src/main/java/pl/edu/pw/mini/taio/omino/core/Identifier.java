package pl.edu.pw.mini.taio.omino.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

public class Identifier implements Comparable<Identifier>{
    private int[] id;
    private Integer hash;

    public Identifier(Block block) {
        Block temp = block;
        id = unzip(block.getPixels());
        for (int i = 0; i < 3; i++) {
            temp = temp.getRotated90();
            int[] next = unzip(temp.getPixels());
            if (compare(id, next) < 0) {
                id = next;
            }
        }
    }

    public int[] unzip(Collection<Pixel> pixels) {
        return pixels.stream()
                .flatMapToInt(p -> IntStream.of(p.getX(), p.getY()))
                .toArray();
    }

    public int compare(int[] id1, int[] id2) {
        if (id1.length != id2.length) return id1.length - id2.length;
        for (int i = 0; i < id1.length; i++) {
            if(id1[i] == id2[i]) continue;
            return id1[i] - id2[i];
        }
        return 0;
    }

    @Override
    public int compareTo(Identifier other) {
        if (id.length != other.id.length) return id.length - other.id.length;
        for (int i = 0; i < id.length; i++) {
            if(id[i] == other.id[i]) continue;
            return id[i] - other.id[i];
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identifier that = (Identifier) o;

        return compareTo(that) == 0;
    }

    @Override
    public int hashCode() {
        if(hash == null) {
            hash = id.length;
            for (int i : id) hash = hash * 119 + i;
        }
        return hash;
    }

    @Override
    public String toString() {
        return "id=" + Arrays.toString(id);
    }
}
