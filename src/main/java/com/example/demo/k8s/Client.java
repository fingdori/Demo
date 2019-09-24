package com.example.demo.k8s;

import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.Watch;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class Client implements Runnable{

    public ArrayList<String> registerModelPrefixList = new ArrayList<String>();
    public ArrayList<String> unRegisterModelPrefixList = new ArrayList<String>();


    public Client() {
        Thread t = new Thread(this);
        t.start();
    }

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
        String kubeConfigPath = "/Users/sanghyunbak/.kube/kube.gpu/config";

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

            for (V1PodCondition pc : listPodConditions) {
                System.out.println("pod condition type : " + pc.getType());
                System.out.println("pod condition message : " + pc.getMessage());
                System.out.println("pod condition reason : " + pc.getReason());
                System.out.println("pod condition status : " + pc.getStatus());
            }
        }


    }



    /**
     * A simple example of how to use Watch API to watch changes in Namespace list.
     */
    public void watch() {

        String kubeConfigPath = "/Users/sanghyunbak/.kube/kube.gpu/config";

        /**
         * listPodForAllNamespacesCall(String _continue
         *                                   String fieldSelector,
         *                                      Boolean includeUninitialized,
         *                                      String labelSelector,
         *                                      Integer limit,
         *                                      String pretty,
         *                                      String resourceVersion,
         *                                      Integer timeoutSeconds,
         *                                      Boolean watch,
         *                                      final ProgressListener progressListener,
         *                                      ProgressRequestListener progressRequestListener) throws ApiException {
         *
         */


    while(true) {
        System.out.println("Start !!! HAHAHAHAHAHAHAHAHA ");
        try {
            // loading the out-of-cluster config, a kubeconfig from file-system
            ApiClient client =
                    ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();

//        ApiClient client = Config.defaultClient();
            client.getHttpClient().setReadTimeout(0, TimeUnit.SECONDS); // infinite timeout
            Configuration.setDefaultApiClient(client);

            CoreV1Api api = new CoreV1Api();

            Watch<V1Pod> watch =
                    Watch.createWatch(
                            client,
                            api.listNamespacedPodCall("default",
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    true,
                                    null,
                                    null),
                            new TypeToken<Watch.Response<V1Pod>>() {
                            }.getType());

            for (Watch.Response<V1Pod> item : watch) {
                if (item.type != null) {
                    System.out.println("type : " + item.type);
                }
                if (item.status != null) {
                    System.out.println("code : {}" + item.status.getCode());
                    System.out.println("status : " + item.status.getStatus());
                }

                V1PodStatus podStatus = item.object.getStatus();
                String name = item.object.getMetadata().getName();
                System.out.println("=========================================");
                System.out.println("name : " + name);
                boolean ready = true;

                if(podStatus == null || podStatus.getContainerStatuses() == null) {
                    System.out.println("pod status is null ...");
                    continue;
                }

                for(V1ContainerStatus cs:podStatus.getContainerStatuses()) {
                    if(cs.getState().getWaiting() != null) {
                        System.out.println("================== waiting ==================");
                        System.out.println("reason : " + cs.getState().getWaiting().getReason());
                        System.out.println("message : " + cs.getState().getWaiting().getMessage());
                    }

                    if(cs.getState().getRunning() != null) {
                        System.out.println("================== Running ==================");
                        System.out.println("reason : " + cs.getState().getRunning().getStartedAt());
                    }

                    if(cs.getState().getTerminated() != null) {
                        System.out.println("================== Terminated ==================");
                        System.out.println("reason : " + cs.getState().getTerminated().getReason());
                        System.out.println("reason : " + cs.getState().getTerminated().getMessage());
                        System.out.println("reason : " + cs.getState().getTerminated().getStartedAt());
                        System.out.println("reason : " + cs.getState().getTerminated().getFinishedAt());
                    }

                    System.out.println("restart count : " + cs.getRestartCount());
                    if(!cs.isReady()) {
                        ready = false;
                        break;
                    }
                }

                if (ready) {
                    System.out.println("[" + String.join("," , registerModelPrefixList) + "]");

                   for (String s : registerModelPrefixList) {
                       if (name.startsWith(s)) {
                           registerModelPrefixList.remove(s);
                           System.out.println("list (" + s + ") is deleted");
                       }
                   }
                }
                System.out.println("ready : " + ready);
                System.out.println("\n");
            }
        } catch (Exception e){
            System.out.println("exception occur!! : " + ExceptionUtils.getStackTrace(e));
        }
    }
    }

    @Override
    public void run() {
        watch();
    }
}
