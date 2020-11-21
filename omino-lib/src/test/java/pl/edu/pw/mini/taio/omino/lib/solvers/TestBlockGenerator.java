package pl.edu.pw.mini.taio.omino.lib.solvers;

import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.lib.generators.BlockGenerator;
import pl.edu.pw.mini.taio.omino.lib.generators.IncrementalBlockGenerator;

import java.util.Arrays;

public class TestBlockGenerator {

    // 0)        1)        2)        3)          4)          5)          6)          7)        8)
    //     ██      ██      ██████    ████████      ██████    ████████    ████        ██████      ██
    //   ████    ██████    ██  ██    ██          ████          ██          ██████    ██        ████████
    // ████        ██                                                                ██
    //
    // 9)          10)       11)           12)       13)       14)       15)     16)       17)
    // ██          ██████    ██████████    ██          ██          ██    ██      ██        ██
    // ████████      ████                  ██████    ██████    ██████    ████    ██████    ██████
    //                                     ██        ██        ██        ████      ██          ██

    private static final BlockGenerator generator = new IncrementalBlockGenerator(5, 0);

    private static Block block(int i) {
        return generator.get(i);
    }

    public static Block[] blocks(int... iii) {
        return Arrays.stream(iii).mapToObj(TestBlockGenerator::block).toArray(Block[]::new);
    }
}
