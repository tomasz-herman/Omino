package pl.edu.pw.mini.taio.omino.lib.generators;

import org.junit.jupiter.api.Test;
import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RandomBlockGeneratorTest {
    @Test
    public void throwsExceptionWhenBlockSizeIsNegative() {
        // given:
        // when:
        // then:
        assertThatThrownBy(() -> new RandomBlockGenerator(-1, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldBeOneBlockWithSizeZero() {
        // given:
        BlockGenerator generator = new RandomBlockGenerator(0, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(1);
    }

    @Test
    public void shouldBeOneBlockWithSizeOne() {
        // given:
        BlockGenerator generator = new RandomBlockGenerator(1, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(1);
    }

    @Test
    public void shouldBeOneBlockWithSizeTwo() {
        // given:
        BlockGenerator generator = new RandomBlockGenerator(2, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(1);
    }

    @Test
    public void shouldBeTwoBlocksWithSizeThree() {
        // given:
        BlockGenerator generator = new RandomBlockGenerator(3, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(2);
    }

    @Test
    public void shouldBeSevenBlocksWithSizeFour() {
        // given:
        BlockGenerator generator = new RandomBlockGenerator(4, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(7);
    }

    @Test
    public void shouldBeEighteenBlocksWithSizeFive() {
        // given:
        BlockGenerator generator = new RandomBlockGenerator(5, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(18);
    }

    @Test
    public void shouldBeSixtyBlocksWithSizeSix() {
        // given:
        BlockGenerator generator = new RandomBlockGenerator(6, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(60);
    }

    @Test
    public void allBlocksShouldBeUnique() {
        // given:
        BlockGenerator generator = new RandomBlockGenerator(6, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(new HashSet<>(blocks)).size().isEqualTo(blocks.size());
    }

    @Test
    public void allStreamCountShouldBeSameAsGeneratorCount() {
        // given:
        BlockGenerator generator = new RandomBlockGenerator(6, 0);
        // when:
        Stream<Block> all = generator.all();
        // then:
        assertThat(all.count()).isEqualTo(generator.count());
    }

    @Test
    public void allBlocksFromGeneratorShouldHaveDesiredSize() {
        // given:
        int size = 6;
        BlockGenerator generator = new RandomBlockGenerator(size, 0);
        // when:
        Collection<Integer> sizes = generator.all()
                .mapToInt(Block::getSize)
                .boxed()
                .collect(Collectors.toSet());
        // then:
        assertThat(sizes).containsOnly(size);
    }
}