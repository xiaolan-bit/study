package hashMap;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {
    public static void main(String[] args) {
        // 创建一个ConcurrentHashMap实例
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        // 向ConcurrentHashMap添加键值对
        concurrentHashMap.put("One", 1);
        concurrentHashMap.put("Two", 2);
        concurrentHashMap.put("Three", 3);

        // 通过多个线程同时修改和访问ConcurrentHashMap
        Thread writerThread = new Thread(() -> {
            concurrentHashMap.put("Four", 4);
            concurrentHashMap.put("One", 5);
            System.out.println("Added Four");
        });

        Thread readerThread = new Thread(() -> {
            Integer value = concurrentHashMap.get("Two");
            System.out.println("Value of Two: " + value);
        });

        writerThread.start();
        readerThread.start();

        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 打印所有键值对
        System.out.println("ConcurrentHashMap content:");
        concurrentHashMap.forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });
    }
}

