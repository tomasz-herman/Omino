package pl.edu.pw.mini.taio.omino.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CutTest {
    @Test
    public void cutWithNonNeighbouringPixelsShouldThrowException() {
        // given:
        Pixel A = new Pixel(1, 2);
        Pixel B = new Pixel(2, 3);
        // when:
        // then:
        assertThatThrownBy(() -> new Cut(new Pixel(A), new Pixel(B))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void cutsWithSamePixelsShouldBeEqual() {
        // given:
        Pixel A = new Pixel(1, 2);
        Pixel B = new Pixel(1, 3);
        Cut C = new Cut(new Pixel(A), new Pixel(B));
        Cut D = new Cut(new Pixel(A), new Pixel(B));
        // when:
        boolean equals = C.equals(D);
        // then:
        assertThat(equals).isEqualTo(true);
    }

    @Test
    public void cutsWithSamePixelsReorderedShouldBeEqual() {
        // given:
        Pixel A = new Pixel(1, 2);
        Pixel B = new Pixel(1, 3);
        Cut C = new Cut(new Pixel(A), new Pixel(B));
        Cut D = new Cut(new Pixel(B), new Pixel(A));
        // when:
        boolean equals = C.equals(D);
        // then:
        assertThat(equals).isEqualTo(true);
    }
}