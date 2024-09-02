## Проект "Облачное хранилище данных"
### Стек используемых технологий
<ul>
<li><b>Backend</b>: Spring Boot, Spring Security, Spring Cloud Gateway</li>
<li><b>База данных</b>: Postgres, Minio, Spring Data JPA</li>
<li><b>Контейнеризация:</b> Docker</li>
</ul>

## Процесс разработки
### 1. Authentication Service
Для аутентификации пользователей воспользуюсь связкой **Spring Security + JWT** с подпиской public/private key
### 2. API Gateway
Реализация единой точки входа с помощью **Spring Cloud Gateway**
Также на уровне gateway реализован алгоритм ограничения трафика Leaky Bucket
### 3. Drive Service
Взаимодействие с файловым хранилием Minio
