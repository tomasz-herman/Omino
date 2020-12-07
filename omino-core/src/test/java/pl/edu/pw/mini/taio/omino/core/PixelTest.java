package pl.edu.pw.mini.taio.omino.core;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class PixelTest {

    @Test
    public void pixelCanBeCopied() {
        // given:
        Pixel pixel = new Pixel(1, 2);
        // when:
        Pixel copied = new Pixel(pixel);
        // then:
        assertThat(pixel).isEqualTo(copied);
    }

    @Test
    public void pixelsWithSameCoordinatesShouldBeEqual() {
        // given:
        Pixel A = new Pixel(1, 2);
        Pixel B = new Pixel(1, 2);
        // when:
        boolean equals = A.equals(B);
        // then:
        assertThat(equals).isEqualTo(true);
    }

    @Test
    public void pixelShouldRotate90() {
        // given:
        Pixel pixel = new Pixel(1, 2);
        // when:
        Pixel rotated = pixel.rotated90();
        // then:
        assertThat(rotated).isEqualTo(new Pixel(2, -1));
    }

    @Test
    public void pixelShouldRotate180() {
        // given:
        Pixel pixel = new Pixel(1, 2);
        // when:
        Pixel rotated = pixel.rotated180();
        // then:
        assertThat(rotated).isEqualTo(new Pixel(-1, -2));
    }

    @Test
    public void pixelShouldRotate270() {
        // given:
        Pixel pixel = new Pixel(1, 2);
        // when:
        Pixel rotated = pixel.rotated270();
        // then:
        assertThat(rotated).isEqualTo(new Pixel(-2, 1));
    }

    @Test
    public void pixelShouldNormalize() {
        // given:
        Pixel pixel = new Pixel(2, 3);
        // when:
        Pixel normalized = pixel.normalized(1, 2);
        // then:
        assertThat(normalized).isEqualTo(new Pixel(1, 1));
    }

    @Test
    public void pixelsWithSameCoordinatesShouldNotBeNeighbours() {
        // given:
        Pixel A = new Pixel(1, 2);
        Pixel B = new Pixel(1, 2);
        // when:
        boolean neighbours = A.isNeighbor(B);
        // then:
        assertThat(neighbours).isEqualTo(false);
    }

    @Test
    public void pixelsNextToEachOtherDiagonallyShouldNotBeNeighbours() {
        // given:
        Pixel A = new Pixel(0, 0);
        Pixel B = new Pixel(1, 1);
        // when:
        boolean neighbours = A.isNeighbor(B);
        // then:
        assertThat(neighbours).isEqualTo(false);
    }

    @Test
    public void pixelsNextToEachOtherHorizontallyShouldBeNeighbours() {
        // given:
        Pixel A = new Pixel(1, 1);
        Pixel B = new Pixel(1, 2);
        // when:
        boolean neighbours = A.isNeighbor(B);
        // then:
        assertThat(neighbours).isEqualTo(true);
    }

    @Test
    public void pixelsNextToEachOtherVerticallyShouldBeNeighbours() {
        // given:
        Pixel A = new Pixel(1, 1);
        Pixel B = new Pixel(2, 1);
        // when:
        boolean neighbours = A.isNeighbor(B);
        // then:
        assertThat(neighbours).isEqualTo(true);
    }

    @Test
    public void pixelsFarAwayFromEachOtherShouldNotBeNeighbours() {
        // given:
        Pixel A = new Pixel(1, 1);
        Pixel B = new Pixel(3, 1);
        // when:
        boolean neighbours = A.isNeighbor(B);
        // then:
        assertThat(neighbours).isEqualTo(false);
    }

    @Test
    public void hashCodeShouldGiveNoMoreThanOnePerMillionConflicts() {
        // given:
        List<Pixel> pixels = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                pixels.add(new Pixel(i, j));
            }
        }
        // when:
        Set<Integer> hashes = pixels.stream()
                .mapToInt(Pixel::hashCode)
                .boxed()
                .collect(Collectors.toSet());
        // then:
        assertThat(hashes).size()
                .isCloseTo(pixels.size(), within(1));
    }
}