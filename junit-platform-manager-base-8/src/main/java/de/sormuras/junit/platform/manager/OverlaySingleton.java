package de.sormuras.junit.platform.manager;

import java.net.URL;
import java.net.URLClassLoader;

public enum OverlaySingleton implements Overlay {
  INSTANCE {
    @Override
    public URLClassLoader newClassLoader(String __, ClassLoader parent, URL... urls) {
      return new URLClassLoader(urls, parent);
    }

    @Override
    public ClassLoader platformClassLoader() {
      URLClassLoader platformClassLoader = new URLClassLoader(new URL[0], null);
      try {
        platformClassLoader.close();
      } catch (Exception e) {
        throw new AssertionError("Closing an empty URLClassLoader failed?!", e);
      }
      return platformClassLoader;
    }
  }
}
