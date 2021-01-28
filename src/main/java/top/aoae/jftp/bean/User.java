package top.aoae.jftp.bean;

/**
 * 配置文件里配置的 User
 */
public class User {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户目录
     */
    private String homeDirectory;

    /**
     * 写权限
     */
    private boolean writePermission = true;
    /**
     * 登录时长
     */
    private int maxIdleTime = 3600;

    public User() {
    }

    public User(String username, String password, String homeDirectory) {
        this.username = username;
        this.password = password;
        this.homeDirectory = homeDirectory;
    }

    public User(String username, String password, String homeDirectory, boolean writePermission) {
        this.username = username;
        this.password = password;
        this.homeDirectory = homeDirectory;
        this.writePermission = writePermission;
    }

    public User(String username, String password, String homeDirectory, boolean writePermission, int maxIdleTime) {
        this.username = username;
        this.password = password;
        this.homeDirectory = homeDirectory;
        this.writePermission = writePermission;
        this.maxIdleTime = maxIdleTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    public boolean isWritePermission() {
        return writePermission;
    }

    public void setWritePermission(boolean writePermission) {
        this.writePermission = writePermission;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", homeDirectory='" + homeDirectory + '\'' +
                ", writePermission=" + writePermission +
                ", maxIdleTime=" + maxIdleTime +
                '}';
    }
}
