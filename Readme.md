# JFtp Server

> JFtp Server是一个基于Java开发的Ftp服务端应用。它简化Ftp服务的部署，并支持多用户，多目录的配置

### 1 启动
#### 平台要求
- 安装`jdk / jre 1.8`及以上版本
#### 运行
```shell
java -jar JFtp-XXX-RELEASE.jar
```
### 2 停用服务
**windwos**
```sh
Control + C
```
**linux/mac**
```sh
kill -9 端口号
```
### 3 配置登录用户信息
#### 第一步：创建配置文件conf.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<config>
    <!--服务端口-->
    <port>21</port>
    <users>
        <!-- 第1个用户 -->
        <user>
            <!--用户名-->
            <username>admin1</username>
            <!--登录密码-->
            <password>123</password>
            <!--访问目录-->
            <homeDirectory>/Users/leyao/j-ftp</homeDirectory>
            <!--写权限-->
            <writePermission>true</writePermission>
            <!--登录时长，单位：秒-->
            <maxIdleTime>7200</maxIdleTime>
        </user>
        <!-- 第2个用户 -->
        <user>
            <username>admin2</username>
            <password>456</password>
            <homeDirectory>/Users/leyao/j-ftp</homeDirectory>
            <writePermission>true</writePermission>
            <maxIdleTime>7201</maxIdleTime>
        </user>
        <!-- 第3个用户 -->
        <user>
            <username>admin3</username>
            <password>789</password>
            <homeDirectory>/Users/leyao/j-ftp</homeDirectory>
            <writePermission>true</writePermission>
            <maxIdleTime>7202</maxIdleTime>
        </user>
    </users>
</config>
```
#### 第二步：启动
```shell
java -jar -Dconfig_file=conf.xml java -jar JFtp-XXX-RELEASE.jar
```

### 4 登录
> 访问：ftp://ip