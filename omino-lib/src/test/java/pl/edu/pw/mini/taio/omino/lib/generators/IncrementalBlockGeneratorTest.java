package pl.edu.pw.mini.taio.omino.lib.generators;

import org.junit.jupiter.api.Test;
import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class IncrementalBlockGeneratorTest {

    @Test
    public void shouldBeOneBlockWithSizeOne() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(1, 0);
        // when:
        Collection<Block> blocks = generator.all();
        // then:
        assertThat(blocks).size().isEqualTo(1);
    }

    @Test
    public void shouldBeOneBlockWithSizeTwo() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(2, 0);
        // when:
        Collection<Block> blocks = generator.all();
        // then:
        assertThat(blocks).size().isEqualTo(1);
    }

    @Test
    public void shouldBeTwoBlocksWithSizeThree() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(3, 0);
        // when:
        Collection<Block> blocks = generator.all();
        // then:
        assertThat(blocks).size().isEqualTo(2);
    }

    @Test
    public void shouldBeSevenBlocksWithSizeFour() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(4, 0);
        // when:
        Collection<Block> blocks = generator.all();
        // then:
        assertThat(blocks).size().isEqualTo(7);
    }

    @Test
    public void shouldBeEighteenBlocksWithSizeFive() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(5, 0);
        // when:
        Collection<Block> blocks = generator.all();
        // then:
        assertThat(blocks).size().isEqualTo(18);
    }

    @Test
    public void shouldBeSixtyBlocksWithSizeSix() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(6, 0);
        // when:
        Collection<Block> blocks = generator.all();
        // then:
        assertThat(blocks).size().isEqualTo(60);
    }

    @Test
    public void allBlocksShouldBeUnique() {
        // given:
        BlockGenerator generator = new IncrementalBlockGenerator(6, 0);
        // when:
        Collection<Block> blocks = generator.all();
        // then:
        assertThat(new HashSet<>(blocks)).size().isEqualTo(blocks.size());
    }
}