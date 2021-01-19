package com.kraftics.krafticslib.tests;

import com.kraftics.krafticslib.config.Config;
import com.kraftics.krafticslib.config.JsonConfig;
import com.kraftics.krafticslib.config.JsonConfiguration;
import org.junit.Test;

import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class KrafticsLibTester {
    final String jsonValidOutput =
            "// This is a start of a header\n" +
            "// Hi\n" +
            "// This is an end of a header\n" +
            "{\n" +
            "  \"name\": \"KrafticsLib\",\n" +
            "  \"version\": \"latest\",\n" +
            "  \"build\": {\n" +
            "    \"id\": 217\n" +
            "  }\n" +
            "}";

    @Test
    public void testJsonConfiguration() {
        JsonConfiguration config = JsonConfiguration.loadConfiguration(new InputStreamReader(KrafticsLibTester.class.getResourceAsStream("/test.json")));
        assertEquals("KrafticsLib", config.getString("name"));
        assertEquals("latest", config.getString("version"));
        assertEquals(153, config.getInt("build.id"));

        config.set("build.id", 217);

        assertEquals(jsonValidOutput, config.saveToString());
    }
}
