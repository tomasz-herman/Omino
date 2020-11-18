package pl.edu.pw.mini.taio.omino.lib.solvers;

import org.junit.jupiter.api.Test;

import java.awt.Dimension;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RectangleSolverTest {

    @Test
    public void throwsExceptionWhenSearchingForRectangleWithNegativeArea() {
        // given:
        int area = -1;
        // when:
        // then:
        assertThatThrownBy(() -> RectangleSolver.getDimensions(area))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void throwsExceptionWhenSearchingForRectangleWithZeroArea() {
        // given:
        int area = 0;
        // when:
        // then:
        assertThatThrownBy(() -> RectangleSolver.getDimensions(area))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void edgesShouldBeFourAndSixForAreaTwentyFour() {
        // given:
        int area = 24;
        // when:
        Dimension dim = RectangleSolver.getDimensions(area);
        // then:
        assertThat(dim).isIn(List.of(new Dimension(4, 6), new Dimension(6, 4)));
    }

    @Test
    public void edgesShouldBeFiveAndFiveForAreaTwentyFive() {
        // given:
        int area = 25;
        // when:
        Dimension dim = RectangleSolver.getDimensions(area);
        // then:
        assertThat(dim).isEqualTo(new Dimension(5, 5));
    }

    @Test
    public void edgesShouldBeOneAndOneForAreaOne() {
        // given:
        int area = 1;
        // when:
        Dimension dim = RectangleSolver.getDimensions(area);
        // then:
        assertThat(dim).isEqualTo(new Dimension(1, 1));
    }
}