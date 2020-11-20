package pl.edu.pw.mini.taio.omino.lib.generators;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VaryingSizeBlockGeneratorTest {
    @Test
    public void throwsExceptionWhenFromParameterNegative() {
        // given:
        // when:
        // then:
        assertThatThrownBy(() -> new VaryingSizeBlockGenerator(-1, 0, getBlockGeneratorFactoryMock()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void throwsExceptionWhenToParameterNegative() {
        // given:
        // when:
        // then:
        assertThatThrownBy(() -> new VaryingSizeBlockGenerator(0, -1, getBlockGeneratorFactoryMock()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void throwsExceptionWhenFromParameterIsEqualToToParameter() {
        // given:
        // when:
        // then:
        assertThatThrownBy(() -> new VaryingSizeBlockGenerator(0, 0, getBlockGeneratorFactoryMock()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void throwsExceptionWhenFromParameterIsGreaterThanToParameter() {
        // given:
        // when:
        // then:
        assertThatThrownBy(() -> new VaryingSizeBlockGenerator(1, 0, getBlockGeneratorFactoryMock()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void throwsExceptionWhenFactoryReturnsNull() {
        // given:
        // when:
        // then:
        assertThatThrownBy(() -> new VaryingSizeBlockGenerator(0, 1, getNullProducingBlockGeneratorFactoryMock()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldBeNinetyBlocksWithSizeFromZeroToSix() {
        // given:
        VaryingSizeBlockGenerator generator = new VaryingSizeBlockGenerator(0, 7, getBlockGeneratorFactoryMock());
        // when:
        int count = generator.count();
        // then:
        assertThat(count).isEqualTo(90);
    }

    @Test
    public void generatedBlocksByManyHaveVaryingSizes() {
        // given:
        VaryingSizeBlockGenerator generator = new VaryingSizeBlockGenerator(0, 7, getBlockGeneratorFactoryMock());
        // when:
        Set<Integer> sizes = generator.many()
                .mapToInt(Block::getSize)
                .limit(1000000)
                .distinct()
                .limit(7)
                .boxed()
                .collect(Collectors.toSet());
        // then:
        assertThat(sizes).containsExactlyInAnyOrderElementsOf(IntStream.range(0, 7).boxed().collect(Collectors.toList()));
    }

    @Test
    public void generatedBlocksByAllHaveVaryingSizes() {
        // given:
        VaryingSizeBlockGenerator generator = new VaryingSizeBlockGenerator(0, 7, getBlockGeneratorFactoryMock());
        // when:
        Set<Integer> sizes = generator.all()
                .mapToInt(Block::getSize)
                .distinct()
                .limit(7)
                .boxed()
                .collect(Collectors.toSet());
        // then:
        assertThat(sizes).containsExactlyInAnyOrderElementsOf(IntStream.range(0, 7).boxed().collect(Collectors.toList()));
    }

    private BlockGeneratorFactory getNullProducingBlockGeneratorFactoryMock() {
        BlockGeneratorFactory factory = Mockito.mock(BlockGeneratorFactory.class);
        Mockito.when(factory.newInstance(Mockito.anyInt(), Mockito.anyLong()))
                .thenReturn(null);
        return factory;
    }

    private BlockGeneratorFactory getBlockGeneratorFactoryMock() {
        BlockGeneratorFactory factory = Mockito.mock(BlockGeneratorFactory.class);
        Mockito.when(factory.newInstance(Mockito.anyInt(), Mockito.anyLong()))
                .thenAnswer( invocation -> {
                    int size = (int) invocation.getArguments()[0];
                    return getBlockGeneratorMock(size);
                });
        return factory;
    }

    private BlockGenerator getBlockGeneratorMock(int size) {
        BlockGenerator generator = Mockito.mock(BlockGenerator.class);
        int count = (size == 0 || size == 1 || size == 2) ? 1 :
        (size == 3) ? 2 :
        (size == 4) ? 7 :
        (size == 5) ? 18 :
        (size == 6) ? 60 : 0;
        Mockito.when(generator.count()).thenReturn(count);
        Block block = Mockito.mock(Block.class);
        Mockito.when(block.getSize()).thenReturn(size);
        Mockito.when(generator.any()).thenReturn(block);
        Mockito.when(generator.get(Mockito.anyInt())).thenReturn(block);
        Mockito.when(generator.many()).thenReturn(Stream.generate(generator::any));
        Mockito.when(generator.all()).thenReturn(Stream.generate(generator::any).limit(count));
        return generator;
    }
}