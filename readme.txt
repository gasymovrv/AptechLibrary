Тема:
    Онлайн библиотека с возможностью покупки книг

Бэк-технологии:
    commons-fileupload
    com.fasterxml.jackson.core
    javax.servlet
    javax.servlet.jsp
    jstl
    log4j // только зависимость в pom.xml, пока не использовал
    mysql
    org.apache.tiles
    org.hibernate
    org.slf4j // только зависимость в pom.xml, пока не использовал
    org.springframework:
        spring-context
        spring-orm
        spring-webmvc
    org.springframework.webflow //настроено, но пока нигде не используется
    org.springframework.security
        spring-security-taglibs

Фронт-технологии:
    css
    html //только в связке с jsp
    jsp //связка с сервером, получение данных из модели
    js/jquery/ajax //динамика на страницах, поиск книг сделан полностью на ajax
    org.apache.tiles //сборка страниц из jsp-файлов
    Twitter Bootstrap 3 //включает готовые стили, используется кастомный шаблон

БД
    mysql
        Размещен на ubuntu-16.04.3-server-amd64,
        установлено на VirtualBox,
        подключение по TCP/IP с локальной машины

Сервер приложений
    wildfly-11.0.0.Final
