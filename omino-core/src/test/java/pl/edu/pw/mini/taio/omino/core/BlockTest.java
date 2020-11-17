package pl.edu.pw.mini.taio.omino.core;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BlockTest {

    @Test
    public void newBlockShouldBeNormalized() {
        // given:
        Block block;
        // when:
        block = new Block(Stream.of(
                new Pixel(1, 1),
                new Pixel(1, 2),
                new Pixel(2, 2),
                new Pixel(2, 1)
        ));
        // then:
        assertThat(block.getPixels())
                .containsExactlyInAnyOrderElementsOf(
                        List.of(
                                new Pixel(0, 0),
                                new Pixel(0, 1),
                                new Pixel(1, 0),
                                new Pixel(1, 1)
                        )
                );
    }

    @Test
    public void blockShouldRotate90() {
        // given:
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(1, 0),
                new Pixel(0, 1),
                new Pixel(0, 2)
        ));
        // when:
        Block rotated = block.getRotated90();
        // then:
        assertThat(rotated.getPixels())
                .containsExactlyInAnyOrderElementsOf(
                        List.of(
                                new Pixel(0, 0),
                                new Pixel(0, 1),
                                new Pixel(1, 1),
                                new Pixel(2, 1)
                        )
                );
    }

    @Test
    public void blockShouldRotate180() {
        // given:
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(1, 0),
                new Pixel(0, 1),
                new Pixel(0, 2)
        ));
        // when:
        Block rotated = block.getRotated180();
        // then:
        assertThat(rotated.getPixels())
                .containsExactlyInAnyOrderElementsOf(
                        List.of(
                                new Pixel(1, 0),
                                new Pixel(1, 1),
                                new Pixel(1, 2),
                                new Pixel(0, 2)
                        )
                );
    }

    @Test
    public void blockShouldRotate270() {
        // given:
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(1, 0),
                new Pixel(0, 1),
                new Pixel(0, 2)
        ));
        // when:
        Block rotated = block.getRotated270();
        // then:
        assertThat(rotated.getPixels())
                .containsExactlyInAnyOrderElementsOf(
                        List.of(
                                new Pixel(0, 0),
                                new Pixel(1, 0),
                                new Pixel(2, 0),
                                new Pixel(2, 1)
                        )
                );
    }

    @Test
    public void blockShouldBeTheSameAsItsRotation() {
        // given:
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(1, 0),
                new Pixel(0, 1),
                new Pixel(0, 2)
        ));
        Block rotated = block.getRotated90();
        // when:
        boolean equals = block.equals(rotated);
        // then:
        assertThat(equals).isEqualTo(true);
    }

}