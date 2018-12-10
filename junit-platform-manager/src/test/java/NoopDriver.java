import de.sormuras.junit.platform.manager.JUnitPlatformDriver;
import de.sormuras.junit.platform.manager.Version;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

class NoopDriver implements JUnitPlatformDriver {
  @Override
  public void debug(String format, Object... args) {}

  @Override
  public void info(String format, Object... args) {}

  @Override
  public void warn(String format, Object... args) {}

  @Override
  public Map<String, Set<Path>> paths() {
    return Collections.emptyMap();
  }

  @Override
  public String version(Version version) {
    return "<noop>";
  }
}