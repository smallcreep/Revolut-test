package com.github.smallcreep.appium.settings;

import com.github.smallcreep.appium.Settings;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.ini4j.Wini;

/**
 * Return appium capabilities from properties file.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class SettingsIniFile implements Settings {

    /**
     * Ini file.
     */
    private final File file;

    /**
     * Section name.
     */
    private final String section;

    /**
     * Ctor.
     * @param file Ini file
     * @param section Section name
     */
    public SettingsIniFile(final File file, final String section) {
        this.file = file;
        this.section = section;
    }

    @Override
    public Map<String, String> capabilities() throws IOException {
        return new Settings.Simple(
            new Wini(file).get(section)
        ).capabilities();
    }
}
