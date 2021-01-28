package top.aoae.jftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.aoae.jftp.bean.Config;
import top.aoae.jftp.bean.User;

import java.io.File;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{

    Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * 全局配置
     */
    public Config config = null;

    /**
     * 构造方法
     * - 读取配置文件
     */
    public App(){
        // 读配置文件
        String configFile = System.getProperty("config_file");
        if (configFile == null) {
            // 没有配置文件，使用默认配置
            List<User> users = new ArrayList<>();
            users.add(new User("admin", "123456", getDefaultHomeDirectory(), true, 7200));
            config = new Config(21, users);
        } else {
            // 使用配置文件的配置
            File cFile = new File(configFile);
            if (!cFile.exists() || !cFile.isFile() || !configFile.endsWith(".xml")) {
                throw new RuntimeException("config_file参数错误");
            }
            try {
                SAXReader reader = new SAXReader();
                Document xml = reader.read(cFile);
                Node portNode = xml.selectSingleNode("//config/port");
                int port = Integer.parseInt(portNode.getStringValue());

                List<User> users = new ArrayList<>();
                List list = xml.selectNodes("//config/users/user");
                for (Object node: list) {
                    User user = new User();
                    Node userNode = (Node)node;
                    user.setUsername(userNode.selectSingleNode("username").getStringValue());
                    user.setPassword(userNode.selectSingleNode("password").getStringValue());
                    user.setHomeDirectory(userNode.selectSingleNode("homeDirectory").getStringValue());
                    user.setWritePermission("true".equals(userNode.selectSingleNode("writePermission").getStringValue()));
                    user.setMaxIdleTime(Integer.parseInt(userNode.selectSingleNode("maxIdleTime").getStringValue()));
                    users.add(user);
                }
                config = new Config(port, users);
            } catch (DocumentException e) {
                e.printStackTrace();
                logger.error("class:{}, message:{}", e.getClass(), e.getMessage());
                System.exit(0); // 读配置信息异常，直接退出
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("class:{}, message:{}", e.getClass(), e.getMessage());
                System.exit(0); // 读配置信息异常，直接退出
            }
        }
    }

    private FtpServer getFtpServer() throws FtpException {
        ListenerFactory factory = new ListenerFactory();
        factory.setPort(config.getPort());

        FtpServerFactory serverFactory = new FtpServerFactory();
        serverFactory.addListener("default", factory.createListener());

        // 获取用户信息
        List<User> users = config.getUsers();
        for (int i=0;i<users.size(); i++) {
            BaseUser user = new BaseUser();
            user.setName(users.get(i).getUsername());
            user.setPassword(users.get(i).getPassword());
            user.setHomeDirectory(users.get(i).getHomeDirectory());
            user.setMaxIdleTime(users.get(i).getMaxIdleTime());
            if (users.get(i).isWritePermission()) {
                List<Authority> authorities = new ArrayList<>();
                // 当前用户分配写权限
                authorities.add(new WritePermission());
                user.setAuthorities(authorities);
            }
            logger.info(users.get(i).toString());
            serverFactory.getUserManager().save(user);
        }
        FtpServer server = serverFactory.createServer();
        return server;
    }

    /**
     * 返回默认的用户共享目录
     * @return
     */
    private String getDefaultHomeDirectory(){
        // 当前系统为win
        boolean isWindowOs = System.getProperty("os.name").toLowerCase().contains("win");
        // 获取当前系统的用户名
        String username = System.getProperty("user.name");
        String path = null;
        if (isWindowOs) {
            path = "C:/" + username;
        } else {
            boolean isMacOs = System.getProperty("os.name").toLowerCase().contains("mac");
            if (isMacOs) {
                path = "/Users/" + username;
            } else {
                // 剩下的就是linux
                path = "/home/" + username;
            }
        }

        if (path != null) {
            File pFile = new File(path, "j-ftp");
            if (pFile.exists() == false) {
                pFile.mkdirs();
            }
            return pFile.getAbsolutePath();
        }
        return  null;
    }

    public static void main( String[] args ) throws FtpException {
        App app = new App();

        FtpServer ftpServer = app.getFtpServer();
        ftpServer.start();

    }
}
