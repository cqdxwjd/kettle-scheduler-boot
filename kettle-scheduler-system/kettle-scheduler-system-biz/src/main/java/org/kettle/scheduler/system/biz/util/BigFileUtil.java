package org.kettle.scheduler.system.biz.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-31 02:57
 */
public class BigFileUtil {

    public static void main(String[] args){
        // 将282兆的文件内容头部添加一行字符  "This is a head!"
        String strHead = "This is a head!" ; // 添加的头部内容
        String srcFilePath = "big_file" ; // 原文件路径
        String destFilePath = "big_file_has_head" ; // 添加头部后文件路径 （最终添加头部生成的文件路径）
        long startTime = System.currentTimeMillis();
        try {
            //映射原文件到内存
            RandomAccessFile srcRandomAccessFile = new RandomAccessFile(srcFilePath, "r");
            FileChannel srcAccessFileChannel = srcRandomAccessFile.getChannel();
            long srcLength = srcAccessFileChannel.size();
            System.out.println("src file size:"+srcLength);  // src file size:296354010
            MappedByteBuffer srcMap = srcAccessFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, srcLength);


            // 映射目标文件到内存
            RandomAccessFile destRandomAccessFile = new RandomAccessFile(destFilePath, "rw");
            FileChannel destAccessFileChannel = destRandomAccessFile.getChannel();
            long destLength = srcLength + strHead.getBytes().length;
            System.out.println("dest file size:"+destLength);  // dest file size:296354025
            MappedByteBuffer destMap = destAccessFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, destLength);

            // 开始文件追加 : 先添加头部内容，再添加原来文件内容
            destMap.position(0);
            destMap.put(strHead.getBytes());
            destMap.put(srcMap);
            destAccessFileChannel.close();
            System.out.println("dest real file size:"+new RandomAccessFile(destFilePath,"r").getChannel().size());
            System.out.println("total time :" + (System.currentTimeMillis() - startTime));// 貌似时间不准确，异步操作？
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
