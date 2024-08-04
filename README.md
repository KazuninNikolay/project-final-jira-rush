## [REST API](http://localhost:8080/doc)

## Список выполненных задач:
1) удалены доступ чере Yandex, VK.
2) Значения чувствительной информации вынесены в файл local-param.properties, сичтываются при старте сервера из переменных окружения машины.
3) Добавлены тесты ProfileRestController.
4) Рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload,c Java IO на Java NIO
5) Добавил новый функционал: добавление тегов к задаче.
6) Написал Dockerfile и Docker-compose. Приложение успешно развертывается в Docker.
