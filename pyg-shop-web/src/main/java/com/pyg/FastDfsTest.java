package com.pyg;

import org.csource.fastdfs.*;

/**
 * Description: 文件系统测试
 *
 * @author AK
 * @date 2018/8/13 9:08
 * @since 1.0.0
 */
public class FastDfsTest {
    public static void main(String[] args) throws Exception {
        // 1、加载配置文件，配置文件中的内容就是 tracker 服务的地址。
        ClientGlobal.init("D:\\AppData\\pyg\\pyg-shop-web\\src\\main\\resources\\config\\fdfs_client.conf");
        // 2、创建一个 TrackerClient 对象。直接 new 一个。
        TrackerClient trackerClient = new TrackerClient();
        // 3、使用 TrackerClient 对象创建连接，获得一个 TrackerServer 对象。
        TrackerServer trackerServer = trackerClient.getConnection();

        // 4、创建一个 StorageServer 的引用，值为 null
        StorageServer storageServer = null;
        // 5、创建一个 StorageClient 对象，需要两个参数 TrackerServer 对象、StorageServer 的引用
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        String[] strings = storageClient.upload_file("D:\\Download\\upload\\001.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);
        }
    }
}