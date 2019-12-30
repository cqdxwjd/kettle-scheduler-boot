package org.kettle.scheduler.system.biz.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.kettle.scheduler.system.biz.entity.FtpFile;

import java.io.*;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * <p>Description: [ftp工具类]</p>
 * Created on 2018/6/4
 *
 * @author 叶向阳
 * @version 1.0
 */
public class FtpClientUtil {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final int DEFAULT_TIMEOUT = 60 * 1000;
    private static final String DAILY_FILE_PATH = "dailyFilePath";
    private String host;
    private int port;
    private String username;
    private String password;
    private FTPClient ftpClient;
    private volatile String ftpBasePath;

    private FtpClientUtil(String host, String username, String password) {
        this(host, 21, username, password, DEFAULT_CHARSET);
        setTimeout(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    private FtpClientUtil(String host, int port, String username, String password, String charset) {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding(charset);
        this.host = StringUtils.isEmpty(host) ? "localhost" : host;
        this.port = (port <= 0) ? 21 : port;
        this.username = StringUtils.isEmpty(username) ? "anonymous" : username;
        this.password = password;
    }

    public FtpClientUtil(String username) {
        this.username = username;
    }

    /**
     * <p>Description:[创建默认的ftp客户端]</p>
     * Created on 2018/6/5
     *
     * @param host     主机名或者ip地址
     * @param username ftp用户名
     * @param password ftp密码
     * @return com.moy.demo.common.utils.FtpCli
     * @author 叶向阳
     */
    public static FtpClientUtil createFtpCli(String host, String username, String password) {
        return new FtpClientUtil(host, username, password);
    }

    /**
     * <p>Description:[创建自定义属性的ftp客户端]</p>
     * Created on 2018/6/5
     *
     * @param host     主机名或者ip地址
     * @param port     ftp端口
     * @param username ftp用户名
     * @param password ftp密码
     * @param charset  字符集
     * @return com.moy.demo.common.utils.FtpCli
     * @author 叶向阳
     */
    public static FtpClientUtil createFtpCli(String host, int port, String username, String password, String charset) {
        return new FtpClientUtil(host, port, username, password, charset);
    }

    /**
     * <p>Description:[设置超时时间]</p>
     * Created on 2018/6/5
     *
     * @param defaultTimeout 超时时间
     * @param connectTimeout 超时时间
     * @param dataTimeout    超时时间
     * @author 叶向阳
     */
    public void setTimeout(int defaultTimeout, int connectTimeout, int dataTimeout) {
        ftpClient.setDefaultTimeout(defaultTimeout);
        //ftpClient.setConnectTimeout(connectTimeout);
        ftpClient.setDataTimeout(dataTimeout);
    }

    /**
     * <p>Description:[连接到ftp]</p>
     * Created on 2018/6/5
     *
     * @author 叶向阳
     */
    public void connect() throws IOException {
        try {
            ftpClient.connect(host, port);
        } catch (UnknownHostException e) {
            throw new IOException("Can't find FTP server :" + host);
        }

        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            disconnect();
            throw new IOException("Can't connect to server :" + host);
        }

        if (!ftpClient.login(username, password)) {
            disconnect();
            throw new IOException("Can't login to server :" + host);
        }

        // set data transfer mode.
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        // Use passive mode to pass firewalls.
        ftpClient.enterLocalPassiveMode();

        initFtpBasePath();
    }

    /**
     * <p>Description:[连接ftp时保存刚登陆ftp时的路径]</p>
     * Created on 2018/6/6
     *
     * @author 叶向阳
     */
    private void initFtpBasePath() throws IOException {
        if (StringUtils.isEmpty(ftpBasePath)) {
            synchronized (this) {
                if (StringUtils.isEmpty(ftpBasePath)) {
                    ftpBasePath = ftpClient.printWorkingDirectory();
                }
            }
        }
    }

    /**
     * <p>Description:[ftp是否处于连接状态，是连接状态返回<tt>true</tt>]</p>
     * Created on 2018/6/5
     *
     * @return boolean  是连接状态返回<tt>true</tt>
     * @author 叶向阳
     */
    public boolean isConnected() {
        return ftpClient.isConnected();
    }

    /**
     * <p>Description:[上传文件到对应日期文件下，
     * 如当前时间是2018-06-06，则上传到[ftpBasePath]/[DAILY_FILE_PATH]/2018/06/06/下]</p>
     * Created on 2018/6/6
     *
     * @param fileName    文件名
     * @param inputStream 文件输入流
     * @return java.lang.String
     * @author 叶向阳
     */
    public String uploadFileToDailyDir(String fileName, InputStream inputStream) throws IOException {
        changeWorkingDirectory(ftpBasePath);
        SimpleDateFormat dateFormat = new SimpleDateFormat("/yyyy/MM/dd");
        String formatDatePath = dateFormat.format(new Date());
        String uploadDir = DAILY_FILE_PATH + formatDatePath;
        makeDirs(uploadDir);
        storeFile(fileName, inputStream);
        return formatDatePath + "/" + fileName;
    }

    /**
     * <p>Description:[根据uploadFileToDailyDir返回的路径，从ftp下载文件到指定输出流中]</p>
     * Created on 2018/6/6
     *
     * @param dailyDirFilePath 方法uploadFileToDailyDir返回的路径
     * @param outputStream     输出流
     * @author 叶向阳
     */
    public void downloadFileFromDailyDir(String dailyDirFilePath, OutputStream outputStream) throws IOException {
        changeWorkingDirectory(ftpBasePath);
        String ftpRealFilePath = DAILY_FILE_PATH + dailyDirFilePath;
        ftpClient.retrieveFile(ftpRealFilePath, outputStream);
    }

    /**
     * <p>Description:[获取ftp上指定文件名到输出流中]</p>
     * Created on 2018/6/5
     *
     * @param ftpFileName 文件在ftp上的路径  如绝对路径 /home/ftpuser/123.txt 或者相对路径 123.txt
     * @param out         输出流
     * @author 叶向阳
     */
    public void retrieveFile(String ftpFileName, OutputStream out) throws IOException {
        try {
            FTPFile[] fileInfoArray = ftpClient.listFiles(ftpFileName);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File '" + ftpFileName + "' was not found on FTP server.");
            }

            FTPFile fileInfo = fileInfoArray[0];
            if (fileInfo.getSize() > Integer.MAX_VALUE) {
                throw new IOException("File '" + ftpFileName + "' is too large.");
            }

            if (!ftpClient.retrieveFile(ftpFileName, out)) {
                throw new IOException("Error loading file '" + ftpFileName + "' from FTP server. Check FTP permissions and path.");
            }
            out.flush();
        } finally {
            closeStream(out);
        }
    }


    /**
     * <p>Description:[将输入流存储到指定的ftp路径下]</p>
     * Created on 2018/6/6
     *
     * @param ftpFileName 文件在ftp上的路径 如绝对路径 /home/ftpuser/123.txt 或者相对路径 123.txt
     * @param in          输入流
     * @author 叶向阳
     */
    public void storeFile(String ftpFileName, InputStream in) throws IOException {
        try {
            if (!ftpClient.storeFile(ftpFileName, in)) {
                throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path.");
            }
        } finally {
            closeStream(in);
        }
    }

    /**
     * <p>Description:[根据文件ftp路径名称删除文件]</p>
     * Created on 2018/6/6
     *
     * @param ftpFileName 文件ftp路径名称
     * @author 叶向阳
     */
    public void deleteFile(String ftpFileName) throws IOException {
        if (!ftpClient.deleteFile(ftpFileName)) {
            throw new IOException("Can't remove file '" + ftpFileName + "' from FTP server.");
        }
    }

    /**
     * <p>Description:[上传文件到ftp]</p>
     * Created on 2018/6/6
     *
     * @param ftpFileName 上传到ftp文件路径名称
     * @param localFile   本地文件路径名称
     * @author 叶向阳
     */
    public void upload(String ftpFileName, File localFile) throws IOException {
        if (!localFile.exists()) {
            throw new IOException("Can't upload '" + localFile.getAbsolutePath() + "'. This file doesn't exist.");
        }

        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(localFile));
            if (!ftpClient.storeFile(ftpFileName, in)) {
                throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path.");
            }
        } finally {
            closeStream(in);
        }
    }

    /**
     * <p>Description:[上传文件夹到ftp上]</p>
     * Created on 2018/6/6
     *
     * @param remotePath ftp上文件夹路径名称
     * @param localPath  本地上传的文件夹路径名称
     * @author 叶向阳
     */
    public void uploadDir(String remotePath, String localPath) throws IOException {
        localPath = localPath.replace("\\\\", "/");
        File file = new File(localPath);
        if (file.exists()) {
            if (!ftpClient.changeWorkingDirectory(remotePath)) {
                ftpClient.makeDirectory(remotePath);
                ftpClient.changeWorkingDirectory(remotePath);
            }
            File[] files = file.listFiles();
            if (null != files) {
                for (File f : files) {
                    if (f.isDirectory() && !f.getName().equals(".") && !f.getName().equals("..")) {
                        uploadDir(remotePath + "/" + f.getName(), f.getPath());
                    } else if (f.isFile()) {
                        upload(remotePath + "/" + f.getName(), f);
                    }
                }
            }
        }
    }

    /**
     * <p>Description:[下载ftp文件到本地上]</p>
     * Created on 2018/6/6
     *
     * @param ftpFileName ftp文件路径名称
     * @param localFile   本地文件路径名称
     * @author 叶向阳
     */
    public void download(String ftpFileName, File localFile) throws IOException {
        OutputStream out = null;
        try {
            ftpClient.changeWorkingDirectory(ftpFileName);
            String s = ftpClient.printWorkingDirectory();
            FTPFile[] fileInfoArray = ftpClient.listFiles(ftpFileName);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File " + ftpFileName + " was not found on FTP server.");
            }

            FTPFile fileInfo = fileInfoArray[0];
            if (fileInfo.getSize() > Integer.MAX_VALUE) {
                throw new IOException("File " + ftpFileName + " is too large.");
            }

            out = new BufferedOutputStream(new FileOutputStream(localFile));
            if (!ftpClient.retrieveFile(ftpFileName, out)) {
                throw new IOException("Error loading file " + ftpFileName + " from FTP server. Check FTP permissions and path.");
            }
            out.flush();
        } finally {
            closeStream(out);
        }
    }


    /**
     * <p>Description:[改变工作目录]</p>
     * Created on 2018/6/6
     *
     * @param dir ftp服务器上目录
     * @return boolean 改变成功返回true
     * @author 叶向阳
     */
    public boolean changeWorkingDirectory(String dir) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            return ftpClient.changeWorkingDirectory(dir);
        } catch (IOException e) {
        }

        return false;
    }

    /**
     * <p>Description:[下载ftp服务器下文件夹到本地]</p>
     * Created on 2018/6/6
     *
     * @param remotePath ftp上文件夹路径名称
     * @param localPath  本地上传的文件夹路径名称
     * @return void
     * @author 叶向阳
     */
    public void downloadDir(String remotePath, String localPath) throws IOException {
        localPath = localPath.replace("\\\\", "/");
        File file = new File(localPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        FTPFile[] ftpFiles = ftpClient.listFiles(remotePath);
        for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            if (ftpFile.isDirectory() && !ftpFile.getName().equals(".") && !ftpFile.getName().equals("..")) {
                downloadDir(remotePath + "/" + ftpFile.getName(), localPath + "/" + ftpFile.getName());
            } else {
                download(remotePath + "/" + ftpFile.getName(), new File(localPath + "/" + ftpFile.getName()));
            }
        }
    }


    /**
     * <p>Description:[列出ftp上文件目录下的文件]</p>
     * Created on 2018/6/6
     *
     * @param filePath ftp上文件目录
     * @return java.util.List<java.lang.String>
     * @author 叶向阳
     */
    //public List<String> listFileNames(String filePath) throws IOException {
    public List<FtpFile> listFileNames(String filePath, List<FtpFile> fileList) throws IOException {
        FTPFile[] ftpFiles = ftpClient.listFiles(filePath);
        List<FtpFile> fileLists = new ArrayList<>();
        if (ftpFiles != null) {
            Arrays.stream(ftpFiles).forEach(ftpFile -> {
                if (ftpFile.isFile()) {
                    fileLists.add(new FtpFile(ftpFile.getName(), filePath + ftpFile.getName(), ftpFile.getSize() / 1024, ftpFile.getTimestamp().getTime()));
                } else if (ftpFile.isDirectory()) {
                    try {
                        listFileNames(filePath + ftpFile.getName() + "/", fileList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //遍历完当前文件夹之后，过滤出最后一条dmp文件
        Stream<FtpFile> dmpStream = fileLists.stream().filter(str -> str.getFileName().endsWith("dmp") == true).sorted(Comparator.comparing(FtpFile::getFileName).reversed());
        try {
            FtpFile file = dmpStream.findFirst().get();
            //System.out.println("文件名：" + file.getFileName() + ";时间：" + file.getTime() + ";路径：" + file.getFilePath() + "大小(KB)" + file.getSize());
            fileList.add(file);
            System.out.println("执行异步线程");
            System.out.println("正在下载文件："+file.getFilePath());
            download(new String(file.getFilePath().getBytes("GBK"), "gb2312"),new File("D:\\archives\\"+file.getFileName()));
            new Thread(()->{
            });
        } catch (NoSuchElementException e) {

        }
        return fileList;
        //return fileList;
    }

    /**
     * 递归遍历出目录下面所有文件
     *
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @throws IOException
     */
    public void List(String pathName) throws IOException {
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            String directory = pathName;
            //更换目录到当前目录
            ftpClient.changeWorkingDirectory(directory);
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    System.out.println("文件名：" + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println("文件夹：" + file.getName() + ";路径：" + directory + file.getName() + "/");
                    List(directory + file.getName() + "/");
                }
            }
        }
    }

    /**
     * <p>Description:[发送ftp命令到ftp服务器中]</p>
     * Created on 2018/6/6
     *
     * @param args ftp命令
     * @author 叶向阳
     */
    public void sendSiteCommand(String args) throws IOException {
        if (!ftpClient.isConnected()) {
            ftpClient.sendSiteCommand(args);
        }
    }

    /**
     * <p>Description:[获取当前所处的工作目录]</p>
     * Created on 2018/6/6
     *
     * @return java.lang.String 当前所处的工作目录
     * @author 叶向阳
     */
    public String printWorkingDirectory() {
        if (!ftpClient.isConnected()) {
            return "";
        }

        try {
            return ftpClient.printWorkingDirectory();
        } catch (IOException e) {
            // do nothing
        }

        return "";
    }

    /**
     * <p>Description:[切换到当前工作目录的父目录下]</p>
     * Created on 2018/6/6
     *
     * @return boolean 切换成功返回true
     * @author 叶向阳
     */
    public boolean changeToParentDirectory() {

        if (!ftpClient.isConnected()) {
            return false;
        }

        try {
            return ftpClient.changeToParentDirectory();
        } catch (IOException e) {
            // do nothing
        }

        return false;
    }

    /**
     * <p>Description:[返回当前工作目录的上一级目录]</p>
     * Created on 2018/6/6
     *
     * @return java.lang.String 当前工作目录的父目录
     * @author 叶向阳
     */
    public String printParentDirectory() {
        if (!ftpClient.isConnected()) {
            return "";
        }

        String w = printWorkingDirectory();
        changeToParentDirectory();
        String p = printWorkingDirectory();
        changeWorkingDirectory(w);

        return p;
    }

    /**
     * <p>Description:[创建目录]</p>
     * Created on 2018/6/6
     *
     * @param pathname 路径名
     * @return boolean 创建成功返回true
     * @author 叶向阳
     */
    public boolean makeDirectory(String pathname) throws IOException {
        return ftpClient.makeDirectory(pathname);
    }

    /**
     * <p>Description:[创建多个目录]</p>
     * Created on 2018/6/6
     *
     * @param pathname 路径名
     * @author 叶向阳
     */
    public void makeDirs(String pathname) throws IOException {
        pathname = pathname.replace("\\\\", "/");
        String[] pathnameArray = pathname.split("/");
        for (String each : pathnameArray) {
            if (StringUtils.isNotEmpty(each)) {
                ftpClient.makeDirectory(each);
                ftpClient.changeWorkingDirectory(each);
            }
        }
    }

    /**
     * <p>Description:[关闭流]</p>
     * Created on 2018/6/6
     *
     * @param stream 流
     * @author 叶向阳
     */
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ex) {
                // do nothing
            }
        }
    }

    /**
     * <p>Description:[关闭ftp连接]</p>
     * Created on 2018/6/6
     *
     * @author 叶向阳
     */
    public void disconnect() {
        if (null != ftpClient && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ex) {
                // do nothing
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FtpClientUtil ftpCli = FtpClientUtil.createFtpCli("10.108.12.6", 21, "rjyf", "ftp@1234", "GBK");
        try {
            ftpCli.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ftpCli.changeWorkingDirectory("gkdata");
        //ftpCli.listFileNames("/gkdata/");
        //ftpCli.changeWorkingDirectory(new String("南昌市".getBytes("GBK"), "gb2312"));
        List<FtpFile> fileList = new ArrayList<>();
        fileList = ftpCli.listFileNames("/", fileList);
        //strings.stream().forEach(str-> System.out.println("文件结尾："+str.endsWith("dmp")));
        //String dmp = strings.stream().filter(str -> str.endsWith("dmp") == true).sorted(Comparator.reverseOrder()).findFirst().get();//forEach(System.out::println);
        //ftpCli.download(filePath, new File("D:\\archives\\"+dmp));
        //strings.sort(String.CASE_INSENSITIVE_ORDER);
        //strings.forEach(str -> System.out.println("fileName:" + str));
    }
}