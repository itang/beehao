package controllers;

import org.apache.commons.io.FileUtils;
import play.mvc.Controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Tests.
 *
 * @author itang
 */
public class Tests extends Controller {
    public static void test() throws IOException {
        URL ap = Thread.currentThread().getContextClassLoader().getResource("application.conf");

        renderText(FileUtils.readFileToString(new File(ap.getFile())));
    }
}
