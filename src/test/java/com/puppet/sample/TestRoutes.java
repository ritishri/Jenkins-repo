package com.puppet.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;


public class TestRoutes 
{
    @BeforeClass
    public static void beforeClass() throws Exception {
      App.main(null);
	  Thread.sleep(1000);
    }
   
    @AfterClass
    public static void afterClass() throws Exception {
	  Thread.sleep(1000);
      Spark.stop();
    }

    @Test
    public void testEnMsg() throws IOException {
        TestResponse res = request("GET", "/");
        assertEquals(200, res.status);
        assertTrue(res.body.contains("Hello World!"));
	}

    private TestResponse request(String method, String path) throws java.io.IOException {
		try {
			URL url = new URL("http://localhost:9999" + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.connect();
			String body = IOUtils.toString(connection.getInputStream());
			return new TestResponse(connection.getResponseCode(), body);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Sending request failed: " + e.getMessage());
			return null;
		}
	}

	private static class TestResponse {

		public final String body;
		public final int status;

		public TestResponse(int status, String body) {
			this.status = status;
			this.body = body;
		}

    }
}
