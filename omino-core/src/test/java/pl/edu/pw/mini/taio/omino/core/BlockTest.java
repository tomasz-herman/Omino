package pl.edu.pw.mini.taio.omino.core;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlockTest {

    @Test
    public void blockWithNoPixelsHasZeroSize() {
        // given:
        Block block;
        // when:
        block = new Block(Stream.of());
        // then:
        assertThat(block.getSize()).isEqualTo(0);
        assertThat(block.getHeight()).isEqualTo(0);
        assertThat(block.getWidth()).isEqualTo(0);
    }

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

    @Test
    public void symmetricalBlocksShouldHaveOnlySelfRotation() {
        // given:
        //    ██
        //  ██████
        //    ██
        Block block = new Block(Stream.of(
                new Pixel(0, 1),
                new Pixel(1, 0),
                new Pixel(1, 1),
                new Pixel(1, 2),
                new Pixel(2, 1)
        ));
        // when:
        Collection<Block> rotations = block.getRotations();
        // then:
        assertThat(rotations).containsExactly(block);
    }

    @Test
    public void halfSymmetricalBlocksShouldHaveTwoRotations() {
        // given:
        //      ██
        //  ██████
        //  ██
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(0, 1),
                new Pixel(1, 1),
                new Pixel(2, 1),
                new Pixel(2, 2)
        ));
        // when:
        Collection<Block> rotations = block.getRotations();
        // then:
        assertThat(rotations).hasSize(2);
    }

    @Test
    public void nonSymmetricalBlocksShouldHaveFourRotations() {
        // given:
        //    ██
        //  ██████
        //  ██
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(0, 1),
                new Pixel(1, 1),
                new Pixel(2, 1),
                new Pixel(1, 2)
        ));
        // when:
        Collection<Block> rotations = block.getRotations();
        // then:
        assertThat(rotations).hasSize(4);
    }

    @Test
    public void duplicatedPixelsAreMergedIntoOne() {
        // given:
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(0, 0)
        ));
        // when:
        Collection<Pixel> pixels = block.getPixels();
        // then:
        assertThat(pixels).hasSize(1);
    }

    @Test
    public void splitShouldPreserveBlockIfCutsAreNotSufficient() {
        // given:
        //  ████
        //  ████
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(0, 1),
                new Pixel(1, 0),
                new Pixel(1, 1)
        ));
        List<Cut> cuts = List.of(new Cut(new Pixel(0, 0), new Pixel(1, 0)));
        // when:
        Collection<Block> blocks = block.split(cuts);
        // then:
        assertThat(blocks).containsExactly(block);
    }

    @Test
    public void splitShouldPreserveBlockIfCutsMisses() {
        // given:
        //    __
        //  ████████
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(1, 0),
                new Pixel(2, 0),
                new Pixel(3, 0)
        ));
        List<Cut> cuts = List.of(new Cut(new Pixel(1, 0), new Pixel(1, 1)));
        // when:
        Collection<Block> blocks = block.split(cuts);
        // then:
        assertThat(blocks).containsExactly(block);
    }

    @Test
    public void splitShouldCutBaguetteInTwoHalves() {
        // given:
        //  ████|████
        Block baguette = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(1, 0),
                new Pixel(2, 0),
                new Pixel(3, 0)
        ));
        List<Cut> cuts = List.of(new Cut(new Pixel(1, 0), new Pixel(2, 0)));
        // when:
        Collection<Block> blocks = baguette.split(cuts);
        // then:
        Block halfBaguette = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(1, 0)
        ));
        assertThat(blocks).containsExactly(halfBaguette, halfBaguette);
    }

    @Test
    public void possibleCutsShouldReturnAllPossibleCuts() {
        // given:
        //  ████
        //  ████
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(0, 1),
                new Pixel(1, 0),
                new Pixel(1, 1)
        ));

        // when:
        Collection<Cut> cuts = block.getPossibleCuts();
        // then:
        assertThat(cuts).containsExactlyInAnyOrderElementsOf(List.of(
                new Cut(new Pixel(0, 0), new Pixel(0, 1)),
                new Cut(new Pixel(0, 0), new Pixel(1, 0)),
                new Cut(new Pixel(1, 1), new Pixel(1, 0)),
                new Cut(new Pixel(1, 1), new Pixel(0, 1))));
    }

    @Test
    public void rotatedBlocksHaveSameColorAsOriginal() {
        // given:
        Color color = Color.CYAN;
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(0, 1),
                new Pixel(1, 1),
                new Pixel(2, 1),
                new Pixel(1, 2)
        ), color);
        // when:
        Collection<Color> colors = block.getRotations().stream()
                .map(Block::getColor)
                .collect(Collectors.toList());
        // then:
        assertThat(colors).containsOnly(color);
    }

    @Test
    public void splitBlocksHaveSameColorAsOriginal() {
        // given:
        Color color = Color.PINK;
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(0, 1),
                new Pixel(1, 1),
                new Pixel(2, 1),
                new Pixel(1, 2)
        ), color);
        // when:
        Collection<Color> colors = IntStream
                .rangeClosed(0, block.getPossibleCuts().size())
                .mapToObj(block::getCutBlocks)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .map(Block::getColor)
                .collect(Collectors.toList());
        // then:
        assertThat(colors).containsOnly(color);
    }

    @Test
    public void settingBlockColorShouldApplyColorsAlsoToItsSplitBlocks() {
        // given:
        Color color = Color.PINK;
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(0, 1),
                new Pixel(1, 1),
                new Pixel(2, 1),
                new Pixel(1, 2)
        ), Color.GREEN);
        Collection<Block> splitBlocks = IntStream
                .rangeClosed(0, block.getPossibleCuts().size())
                .mapToObj(block::getCutBlocks)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        // when:
        block.setColor(color);
        Collection<Color> colors = splitBlocks.stream()
                .map(Block::getColor)
                .collect(Collectors.toList());
        // then:
        assertThat(colors).containsOnly(color);
    }

    @Test
    public void settingBlockColorShouldApplyColorsAlsoToItsRotations() {
        // given:
        Color color = Color.PINK;
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(0, 1),
                new Pixel(1, 1),
                new Pixel(2, 1),
                new Pixel(1, 2)
        ), Color.GREEN);
        Collection<Block> rotations = block.getRotations();
        // when:
        block.setColor(color);
        Collection<Color> colors = rotations.stream()
                .map(Block::getColor)
                .collect(Collectors.toList());
        // then:
        assertThat(colors).containsOnly(color);
    }

    @Test
    public void modifyingPixelsCollectionShouldThrowException() {
        // given:
        Block block = new Block(Stream.of(
                new Pixel(0, 0),
                new Pixel(0, 1),
                new Pixel(1, 1),
                new Pixel(2, 1),
                new Pixel(1, 2)
        ), Color.GREEN);
        // when:
        // then:
        assertThatThrownBy(() -> block.getPixels().add(new Pixel(0, 0))).isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> block.getPixels().remove(new Pixel(0, 0))).isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> block.getPixels().clear()).isInstanceOf(UnsupportedOperationException.class);
    }

}