package com.example.demo.k8s;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodCondition;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Client {
    public static void k8sTest() throws IOException, ApiException {
        ApiClient client = ClientBuilder.cluster().build();

        // set the global default api-client to the in-cluster one from above
        Configuration.setDefaultApiClient(client);

        // the CoreV1Api loads default api-client from global configuration.
        CoreV1Api api = new CoreV1Api();

        // invokes the CoreV1Api client
        V1PodList list;
        list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, false);
        for (V1Pod item : list.getItems()) {
            System.out.println(item.getMetadata().getName());
        }
    }

    public static void runWithKubeConfig() throws IOException, ApiException {
        // file path to your KubeConfig
        String kubeConfigPath = "/Users/sanghyunbak/.kube/config";

        // loading the out-of-cluster config, a kubeconfig from file-system
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();

        // set the global default api-client to the in-cluster one from above
        Configuration.setDefaultApiClient(client);

        // the CoreV1Api loads default api-client from global configuration.
        CoreV1Api api = new CoreV1Api();


        // invokes the CoreV1Api client
        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, false);

        for (V1Pod item : list.getItems()) {
            System.out.println("==============================================================");
            System.out.println(item.getMetadata().getName());
            System.out.println("item.getStatus().getReason() : " + item.getStatus().getReason());
            System.out.println("item.getStatus().getPhase() : " + item.getStatus().getPhase());

            List<V1PodCondition> listPodConditions = item.getStatus().getConditions();

            for (V1PodCondition pc:listPodConditions) {
                System.out.println("pod condition type : " + pc.getType());
                System.out.println("pod condition message : " + pc.getMessage());
                System.out.println("pod condition status : " + pc.getStatus());
            }
        }


    }
}
