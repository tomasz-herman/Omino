package pl.edu.pw.mini.taio.omino.lib.generators;

import org.junit.jupiter.api.Test;
import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IncrementalBlockGeneratorTest {

    @Test
    public void throwsExceptionWhenBlockSizeIsNegative() {
        // given:
        // when:
        // then:
        assertThatThrownBy(() -> new IncrementalBlockGenerator(-1, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldBeOneBlockWithSizeZero() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(0, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(1);
    }

    @Test
    public void shouldBeOneBlockWithSizeOne() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(1, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(1);
    }

    @Test
    public void shouldBeOneBlockWithSizeTwo() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(2, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(1);
    }

    @Test
    public void shouldBeTwoBlocksWithSizeThree() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(3, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(2);
    }

    @Test
    public void shouldBeSevenBlocksWithSizeFour() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(4, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(7);
    }

    @Test
    public void shouldBeEighteenBlocksWithSizeFive() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(5, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(18);
    }

    @Test
    public void shouldBeSixtyBlocksWithSizeSix() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(6, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(blocks).size().isEqualTo(60);
    }

    @Test
    public void allBlocksShouldBeUnique() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(6, 0);
        // when:
        Collection<Block> blocks = generator.all().collect(Collectors.toList());
        // then:
        assertThat(new HashSet<>(blocks)).size().isEqualTo(blocks.size());
    }
}