package pl.edu.pw.mini.taio.omino.lib.generators;

@FunctionalInterface
public interface BlockGeneratorFactory {
    BlockGenerator newInstance(int size, long seed);
}
