/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.concurrent.TimeUnit;

public class ApplicationConsumer {
    /**
     * In order to make sure multicast registry works, need to specify '-Djava.net.preferIPv4Stack=true' before
     * launch the application
     */
    public static void main(String[] args) throws InterruptedException {

        // new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         invoke();
        //         try {
        //             TimeUnit.MINUTES.sleep(10);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }).start();
        //
        // new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         invoke();
        //         try {
        //             TimeUnit.MINUTES.sleep(10);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }).start();
        // // genericInvoke();
        //
        // TimeUnit.MINUTES.sleep(10);

        invoke();

        // genericInvoke();
        // genericInvoke1();

    }

    private static void invoke(){
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("dubbo-demo-api-consumer"));
        reference.setRegistry(new RegistryConfig("multicast://224.5.6.7:1234"));
        reference.setInterface(DemoService.class);
        reference.setConnections(3);

        // reference.setGeneric(true);
        // reference.setRetries(3); //retries
        DemoService service = reference.get();
        String message = service.sayHello("dubbo");
        System.out.println(message);
    }

    private static void genericInvoke(){

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("dubbo-demo-api-consumer"));
        reference.setRegistry(new RegistryConfig("multicast://224.5.6.7:1234"));
        reference.setInterface(DemoService.class);
        reference.setGeneric(true);

        GenericService service = reference.get();
        String message = (String) service.$invoke("sayHello", new String[]{String.class.getName()}, new Object[]{"Shuai"});
        System.out.println(message);

    }

    private static void genericInvoke1(){

        ReferenceConfig<GenericServiceTest> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("dubbo-demo-api-consumer"));
        reference.setRegistry(new RegistryConfig("multicast://224.5.6.7:1234"));
        reference.setInterface(DemoService.class);
        reference.setGeneric(true);

        GenericServiceTest service = reference.get();
        String message = (String) service.$invoke1("sayHello", new String[]{String.class.getName()}, new Object[]{"Shuai"});
        System.out.println(message);

    }

}
