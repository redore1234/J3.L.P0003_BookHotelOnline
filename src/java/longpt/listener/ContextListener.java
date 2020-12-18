/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.LogManager;

/**
 * Web application lifecycle listener.
 *
 * @author phamt
 */
public class ContextListener implements ServletContextListener {

    private final org.apache.log4j.Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Map<String, String> listMap = new HashMap<>();
        String realPath = context.getRealPath("/") + "WEB-INF\\mapfunction.txt";
        File file = new File(realPath);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, "=");
                String key = tokenizer.nextToken();
                String value = tokenizer.nextToken();
                listMap.put(key, value);
            }
        } catch (FileNotFoundException ex) {
            logger.error("ContextListener _ FileNotFoundException: " + ex.getMessage());
        } catch (IOException ex) {
            logger.error("ContextListener _ IOException: " + ex.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("ContextListener BufferClose _ FileNotFoundException: " + e.getMessage());
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    logger.error("ContextListener FileReaderClose _ FileNotFoundException: " + e.getMessage());
                }
            }
        }
        context.setAttribute("MAP", listMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
