package com.example.demo.k8s;

import io.kubernetes.client.ApiException;
import org.junit.Test;

import java.io.IOException;

import static com.example.demo.k8s.Client.k8sTest;
import static com.example.demo.k8s.Client.watch;
import static org.junit.Assert.*;

public class ClientTest {
    @Test
    public void testK8s() {
        try {
            k8sTest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runWithKubeConfig() throws IOException, ApiException {
        Client.runWithKubeConfig();
    }

    @Test
    public void watchTest() {
        try {
            watch();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}