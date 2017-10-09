package com.github.smallcreep.appium;

import java.io.IOException;
import java.util.Map;
import org.cactoos.map.StickyMap;

/**
 * Return appium capabilities.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Settings {

    /**
     * Get Appium capabilities.
     * @return Appium capabilities
     * @throws IOException If read fails
     */
    Map<String, String> capabilities() throws IOException;

    /**
     * Immutable appium capabilies implementation.
     */
    final class Simple implements Settings {

        /**
         * Origin appium capabilities.
         */
        private final Map<String, String> origin;

        /**
         * Ctor.
         * @param origin Origin appium capabilities
         */
        public Simple(final Map<String, String> origin) {
            this.origin = new StickyMap<>(origin);
        }

        @Override
        public Map<String, String> capabilities() {
            return this.origin;
        }
    }
}
