package pl.edu.pw.mini.taio.omino.lib.solvers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.edu.pw.mini.taio.omino.lib.solvers.TestBlockGenerator.blocks;

class FastSquareTest {

    private static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(blocks(7, 2, 2, 4, 9), 5),
                Arguments.of(blocks(11, 10, 10, 10, 10), 5),
                Arguments.of(blocks(10, 12, 2, 15, 7), 5),
                Arguments.of(blocks(8, 5, 14, 14, 7), 5),
                Arguments.of(blocks(7, 7, 17, 17, 4, 11, 11), 6),
                Arguments.of(blocks(3, 0, 14, 5, 13, 9, 0), 6),
                Arguments.of(new Block[]{new Block(Stream.of())}, 0),
                Arguments.of(blocks(0), 3)
        );
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void test(Block[] blocks, int size) {
        // given:
        Solver solver = new FastSquare(blocks);
        // when:
        Block[][] board = solver.solve();
        // then:
        assertThat(board.length).isGreaterThanOrEqualTo(size);
    }

}