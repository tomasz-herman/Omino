package pl.edu.pw.mini.taio.omino.core;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Identifier implements Comparable<Identifier>{
    private List<Integer> id;

    public Identifier(Block block) {
        Block temp = block;
        id = unzip(block.getPixels());
        for (int i = 0; i < 3; i++) {
            temp = temp.getRotated();
            List<Integer> next = unzip(temp.getPixels());
            if (compare(id, next) < 0) {
                id = next;
            }
        }
    }

    public List<Integer> unzip(Collection<Pixel> pixels) {
        return pixels.stream()
                .flatMap(p -> Stream.of(p.getX(), p.getY()))
                .collect(Collectors.toList());
    }

    public int compare(List<Integer> id1, List<Integer> id2) {
        if (id1.size() != id2.size()) return id1.size() - id2.size();
        for (int i = 0; i < id1.size(); i++) {
            if(id1.get(i).equals(id2.get(i))) continue;
            return id1.get(i) - id2.get(i);
        }
        return 0;
    }

    @Override
    public int compareTo(Identifier other) {
        if (id.size() != other.id.size()) return id.size() - other.id.size();
        for (int i = 0; i < id.size(); i++) {
            if(id.get(i).equals(other.id.get(i))) continue;
            return id.get(i) - other.id.get(i);
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
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
