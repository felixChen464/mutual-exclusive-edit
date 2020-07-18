
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