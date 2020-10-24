package spi;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * @author shuyan
 */
public class SpiTest {
    public static void main(String[] args) {
        ServiceLoader<SpiTestInterface> spiTestInterfaces = ServiceLoader.load(SpiTestInterface.class);
        Optional<SpiTestInterface> first = StreamSupport.stream(spiTestInterfaces.spliterator(), false).findFirst();
        first.ifPresent(SpiTestInterface::test);
    }
}
