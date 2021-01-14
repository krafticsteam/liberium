package com.kraftics.krafticslib.tests;

import com.kraftics.krafticslib.config.JsonConfiguration;
import org.junit.Test;

import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class JsonTester {

    @Test
    public void testJsonConfiguration() {
        JsonConfiguration config = JsonConfiguration.loadConfiguration(new InputStreamReader(JsonTester.class.getResourceAsStream("/test.json")));
        assertEquals("KrafticsLib", config.getString("name"));
        assertEquals("latest", config.getString("version"));
        assertEquals(153, config.getInt("build.id"));

        config.set("build.id", 217);

        assertEquals("{\"name\":\"KrafticsLib\",\"version\":\"latest\",\"build\":{\"id\":217}}", config.saveToString());
    }
}
