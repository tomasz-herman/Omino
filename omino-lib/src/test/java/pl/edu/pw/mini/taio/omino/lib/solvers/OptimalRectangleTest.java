package pl.edu.pw.mini.taio.omino.lib.solvers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.edu.pw.mini.taio.omino.lib.solvers.TestBlockGenerator.blocks;

class OptimalRectangleTest {

    private static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(blocks(0), 2),
                Arguments.of(blocks(10), 2),
                Arguments.of(blocks(4), 1),
                Arguments.of(blocks(5), 1),
                Arguments.of(blocks(11), 0),
                Arguments.of(blocks(11, 12), 1),
                Arguments.of(blocks(11, 11), 0),
                Arguments.of(blocks(11, 6), 1),
                Arguments.of(blocks(13, 15), 1),
                Arguments.of(blocks(11, 11), 0),
                Arguments.of(blocks(16, 16), 2),
                Arguments.of(blocks(15, 16), 1),
                Arguments.of(blocks(1, 1, 1), 3),
                Arguments.of(blocks(11, 11, 11), 0),
                Arguments.of(new Block[]{new Block(Stream.of())}, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void test(Block[] blocks, int cuts) {
        // given:
        RectangleSolver solver = new OptimalRectangle(blocks);
        // when:
        solver.solve();
        // then:
        assertThat(solver.getCuts()).isEqualTo(cuts);
    }

}