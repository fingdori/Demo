package com.example.demo.service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CommandServiceImpl implements CommandService {

    private final static Logger logger = LogManager.getLogger("CommandServiceImpl");

    @Override
    public void runCommand(String command) throws IOException {
        // test for python3 script
        CommandLine commandLine = CommandLine.parse("python");
        commandLine.addArgument("/Users/sanghyunbak/IdeaProjects/demo/src/main/python/test.py");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        CommandLine commandline = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        exec.setStreamHandler(streamHandler);
        exec.execute(commandLine);

        BufferedReader bufferedReader = new BufferedReader(new StringReader(new String(outputStream.toByteArray())));

        String line;
        while ((line = bufferedReader.readLine()) != null){
            logger.info("line : " + line);
        }
    }
}
