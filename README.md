
## 环境准备
应用依赖于redis、mysql和本地文件系统，需要在启动前做如下配置：

##### 文件路径：
    应用采用了本地文件系统，默认文件存储位置为/home/edit_file/store,如果路径不存在在应用启动时会自动创建目录，
    如要修改文件的存储路径，请确保路径未被写保护
    
##### redis:
应用中的锁依赖于redis，需要redis版本高于2.8，需要在application.yml中修改redis的访问信息

     spring:
        redis：
            host: 127.0.0.1
            port: 6379
            password:
            database: 0
     
##### 数据库初始化：
需要配置mysql所运行的地址端口以及db名字，修改配置文件当中如下字段：
    
    spring:
        datasource:
            name: test
            url: jdbc:mysql://127.0.0.1:3306/project?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
            username: root
            password: felix

请在应用运行前在数据库运行以下初始化脚本：
   
    CREATE DATABASE project DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
    use project;
    
    CREATE TABLE IF NOT EXISTS `file_record`(
    
    `id` varchar(36) PRIMARY KEY,
    `fileName` varchar(256),
    `createTime` TIMESTAMP,
    `modifyTime` TIMESTAMP,
    `fileCode` varchar(36)
    );
    
如需修改db的名字请务必修改application.yml中的spring.datasource.url

## 实现补充说明

1、用户区分：由于不需要登录以及账号信息，为了区分不同用户，在进入编辑页面的时候使用了uuid生成用户id并存储在cookie当中（1天后过期），若要区别两个用户，需要使用不同的浏览器来进行操作，共享同一个cookie的页面会被认为是同一个用户

2、文件锁的时间：为了让用户知道当前编辑处于什么状态，页面中添加了当前在编辑或是文件被锁住的提示以及倒计时，锁的时间在配置文件当中进行修改。锁的时间只在查询时获取一次，在前端进行倒数，减少获取锁的请求次数

3、编辑时间：在用户对应的可编辑时间结束后，未保存的文本将会丢失，当前文本会被锁住，用户可以点击尝试编辑再次获取编辑权限

4、被锁的用户：未获得编辑权限的用户只会显示文本内容以及文件名，无法进行修改，只能在时间到期后进行尝试编辑

5、锁的实现：考虑到锁的并发性能，采用了redis实现锁，加锁和过期实现了原子性，不会出现加锁后出异常导致锁无法释放的情况，如果是单机场景可以考虑使用本地缓存进行实现，实现LockService进行替换，缺点是应用重启锁会失效

6、文件内容接口：当前为获取内容整体输出，在正式场景中应该实现分页加载或其他方式，这里由于时间原因简单实现，限制了文本长度为100000，限制文件名为50，
以防止恶意输入

7、可能存在的其他优化点：
   7.1 文件名重复校验
   7.2 获取文件列表分页
   7.3 可以使用websocket在对应文件被更新完毕以后，通知对应客户端进行刷新
   
## 运行应用
请将application.yml文件与mutual-exclusive-edit.jar放置与同一路径，启动命令为：
java -jar mutual-exclusive-edit.jar -Dspring.config.location=./application.yml &