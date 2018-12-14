import static de.sormuras.junit.platform.isolator.Configuration.Basic.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import de.sormuras.junit.platform.isolator.Configuration;
import de.sormuras.junit.platform.isolator.ConfigurationBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class ConfigurationTests {

  @Test
  void checkConstants() {
    assertEquals("de.sormuras.junit-platform-isolator", WORKER_GROUP);
    assertEquals("junit-platform-isolator-worker", WORKER_ARTIFACT);
    assertEquals("1.0.0-SNAPSHOT", WORKER_VERSION);
  }

  @TestFactory
  DynamicNode[] checkAllDefaultValues() {
    Configuration configuration = new Configuration();
    Configuration.Basic basic = configuration.basic();
    Configuration.Discovery discovery = configuration.discovery();
    Configuration.Launcher launcher = configuration.launcher();

    return new DynamicNode[] {
      // basic
      dynamicTest("dryRun", () -> assertFalse(basic.isDryRun())),
      dynamicTest("failIfNoTests", () -> assertTrue(basic.isFailIfNoTests())),
      dynamicTest("platformClassLoader", () -> assertTrue(basic.isPlatformClassLoader())),
      dynamicTest("defaultAssertionStatus", () -> assertTrue(basic.isDefaultAssertionStatus())),
      dynamicTest("workerClassName", () -> assertFalse(basic.getWorkerClassName().isEmpty())),
      dynamicTest("workerCoordinates", () -> assertFalse(basic.getWorkerCoordinates().isEmpty())),
      // discovery
      dynamicTest("parameters", () -> assertEmpty(discovery.parameters.keySet())),
      dynamicTest("selectedClasspathRoots", () -> assertEmpty(discovery.selectedClasspathRoots)),
      dynamicTest("filterTagsIncluded", () -> assertEmpty(discovery.filterTagsIncluded)),
      // launcher
      dynamicTest("engine", () -> assertTrue(launcher.testEngineAutoRegistration)),
      dynamicTest("listener", () -> assertTrue(launcher.testExecutionListenerAutoRegistration))
    };
  }

  @Test
  void serialization() {
    var configuration =
        new ConfigurationBuilder()
            .discovery()
            .setSelectedClasspathRoots(Set.of("a/b"))
            .setFilterTagsIncluded(List.of("x | !y"))
            .setParameters(Map.of("a", "b"))
            .end()
            .setDryRun(true)
            .build();

    var bytes = configuration.toBytes();
    var second = Configuration.fromBytes(bytes);
    assertEquals(configuration, second);
  }

  private static void assertEmpty(Collection<?> collection) {
    assertTrue(collection.isEmpty());
  }
}
