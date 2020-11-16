package pl.edu.pw.mini.taio.omino.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        pixel.normalize(1, 2);
        // then:
        assertThat(pixel).isEqualTo(new Pixel(1, 1));
    }

}