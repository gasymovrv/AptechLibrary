# Тема: Онлайн-библиотека с возможностью покупки книг
![](https://github.com/gasymovrv/AptechLibrary/blob/master/src/main/webapp/resources/img/BeSmart-logo.png)

# Бэк-технологии
+ commons-fileupload
+ com.fasterxml.jackson.core
+ java SE 8
+ javax.servlet
+ javax.servlet.jsp
+ jstl
+ log4j (только зависимость в pom.xml, пока не использовал)
+ mysql
+ org.apache.tiles
+ org.hibernate
+ org.slf4j (только зависимость в pom.xml, пока не использовал)
+ org.springframework:
    + spring-context
    + spring-orm
    + spring-webmvc
+ org.springframework.webflow (настроено, но пока нигде не используется)
+ org.springframework.security
    + spring-security-taglibs

# Фронт-технологии
+ css
+ html
+ jsp
+ js/jquery/ajax (поиск книг сделан полностью на ajax)
+ org.apache.tiles (сборка страниц из jsp-файлов)
+ Twitter Bootstrap 3 (включает готовые стили, используется кастомный шаблон)

# БД
+ mysql
    + Размещен на ubuntu-16.04.3-server-amd64,
        установлено на VirtualBox,
        подключение по TCP/IP с локальной машины
+ liquibase
     + Инструмент миграции БД

# Инструмент сборки
Apache Maven 3

# Сервер приложений
wildfly-11.0.0.Final

# Действия, необходимые для заупска
+ Подключить в VirtualBox готовый диск **ub-serv-with-mysql.vbox** (логин/пароль/ip - rgasimov/4/192.168.56.200) или создать новую ВМ **ubuntu-16.04.3-server-amd64** с установленным MySQL и настройками сети:
    + Host-only adapter: VirtualBox Host-Only Ethernet Adapter #2
    + В глобальных настройках для 'VirtualBox Host-Only Ethernet Adapter #2' указан IPv4 addres = 192.168.56.1
    + Файл настроек сети на ubuntu **/etc/network/interfaces**
    должен выглядеть примерно так (главное чтобы совпадали адреса):
        ```
        source /etc/network/interfaces.d/*
    
        # The loopback network interface
        auto lo
        iface lo inet loopback
        
        # The primary network interface
        #auto enp0s3
        #iface enp0s3 inet dhcp
        
        # My host-only adapter (for 1: enp0s3, fro 2: enp0s8)
        auto enp0s3
        iface enp0s3 inet static
        address 192.168.56.200
        netmask 255.255.255.0
        ```
+ **MySQL** на ВМ с настройками (если **НЕ** подключен диск **ub-serv-with-mysql.vbox**):
    + Логин/пароль - aplib_owner/4
    + Кодировка. Изменить файл настроек MySQL:
        + ```sudo nano /etc/mysql/my.cnf``` или, если там пусто, то: ```sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf```
        + Изменить или добавить код в конце, если таких строк не будет:
            ```
            skip-character-set-client-handshake
            character-set-server = utf8
            init-connect='SET NAMES utf8'
            collation-server=utf8_general_ci
            ```
    + Сеть.
        Изменяем адреса, которые будет слушать mysql в файле в секции [mysqlid]:
        + ```sudo nano /etc/mysql/my.cnf``` или, если там нет такой секции, то ```sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf```
        + Вместо: ```bind address=127.0.0.1``` вписываем: ```bind address=0.0.0.0```
        + Перезагружаем: ```service mysql restart```
    + Привелегии.
        Заходим в mysql через консоль и создаем пользователей:
        + Если при установке mysql был задан пароль для root, то входим так ```sudo mysql --user=root --password=4```
        + Создаем юзеров и права для них:
            ```
            CREATE USER 'owner' IDENTIFIED BY '4';
            CREATE USER 'aplib_owner' IDENTIFIED BY '4';
            GRANT ALL PRIVILEGES ON *.* TO 'owner';
            GRANT ALL PRIVILEGES ON aplib.* TO 'aplib_owner';
            FLUSH PRIVILEGES;
            ```
+ **MySQLWorkbench** - если **НЕ** подключен диск **ub-serv-with-mysql.vbox**, то потребуется для дампа данных в БД, иначе по желанию.
+ **База данных**. БД можно восстановить так (если **НЕ** подключен диск **ub-serv-with-mysql.vbox**):
    + Через MySQLWorkbench импортировать скрипты:
        + Настройки подключения через MySQLWorkbench:
            + Host: 192.168.56.200
            + Port: 3306
            + Username: aplib_owner
            + Password: 4
        + Если требуются данные, то: db/dump(struct-and-data).sql
        + Если только структура, то: dump(struct).sql
    + Накатить db/liquibase/liquibase_db.xml
    (Запускать через мавен-плагин, выполнить liquibase:update)
+ **wildfly-11.0.0.Final** с настройками:
     + В ...\wildfly-11.0.0.Final\standalone\configuration\standalone.xml
     для увеличения размера переваемых файлов до 500 Мб прописать:
        ```xml
        <http-listener
            name="default"
            socket-binding="http"
            max-post-size="504857600"
            redirect-socket="https"
            enable-http2="true"
        />
        ```
    + Задеплоить mysql-connector-java-5.1.46.jar
    + В 'Configuration: Subsystems    Subsystem: Datasources    Type: Non-XA' добавить datasource с задеплоенным jdbc и указать настройки:
        + User name: aplib_owner
        + Password: 4
        + JNDI: java:/ApLib
        + Connection URL: jdbc:mysql://192.168.56.200:3306/aplib
+ **Maven 3**
+ **Java 8**
+ **Исходный код**. Можно сфетчить с GitHub или скачать в архиве. Запустить в Intellij IDEA или любой другой среде (хотя возможно потребуются доп. действия). Можно просто собрать мавеном, не используя среду разрабоотки . Код компилируется в war-файл, который небоходимо разместить на WildFly. Затем перейти по ссылке http://localhost:8080/aptech-library/home