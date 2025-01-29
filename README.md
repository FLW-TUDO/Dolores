# FLW Planspiel / FLW Businessgame

This is the official repository for the new FLW Businessgame [Link](http://log-bg.mb.tu-dortmund.de/).

This repository includes the 
- Dolores Backend, a spring boot application that implements the business logic of the game
- Dolores Frontend, a vue frontend that serves as access point for user and admins

## Setup

### Normal Setup

1. Clone repository
2. Adapt nginx.conf if necessary
3. Update the ENVIRONMENT VARIABLES in the docker-compose.yaml (explanation in the next section)
4. Run dolores with ```docker-compose up```

### ENVIRONMENT VARIABLES

The backend and database expect certain environment variables these are as follows for the individual services:

backend:
1. SECRET_KEY: is used for JWT verification of user authorization
2. MONGO_ROOT_USERNAME: username of the mongodb database IMPORTANT: has to be the same as for the db service
3. MONGO_ROOT_PASSWORD: password for the mongodb username IMPORTANT: has to be the same as for the db service

db:
1. MONGO_INITDB_ROOT_USERNAME: username of the mongodb database IMPORTANT: has to be the same as for the backend service
2. MONGO_INITDB_ROOT_PASSWORD: password for the mongodb username IMPORTANT: has to be the same as for the backend service

IMPORTANT: ALL KEYS SHOULD BE RANDOM LIKE: ```DP72QC1LSSCGUDVZXOFWTGM5EGAPY9PT```

### SSL Setup

If you want to use SSL with this project you need to specify the location of the key files in the SSL section of the nginx.conf as follows:

```
  ssl_certificate /path/to/log-bg.mb.tu-dortmund.de/fullchain.pem;
  ssl_certificate_key /path/to/log-bg.mb.tu-dortmund.de/privkey.pem;
```

### Setup with certbot

If you want to run the server using SSL and have no valid certificate you can setup this project using certbot.

1. Clone repository
2. Remove SSL configuration from nginx.conf
3. Start server with ```docker-compose up```
4. Run certbot ```docker-compose run --rm certbot certonly --webroot --webroot-path /var/www/certbot/ -d example.org```
5. Re-add SSL configuration in nginx.conf

Certificate renewal

Simply run
```docker compose run --rm certbot renew```

## Docker Services Explanation

- backend: runs the spring boot server as specified in the backend [Dockerfile](https://github.com/FLW-TUDO/Dolores-Backend/blob/main/Dockerfile)
- nginx: runs a proxy for the vue frontend using nginx and the [nginx.conf](https://github.com/FLW-TUDO/Dolores/blob/main/nginx/nginx.conf)
- db: runs a mongodb database used for storing all game data
- watchtower: used for checking all running containers for updates
- certbot (optional): used for creating custom SSL certificates (see section Setup with certbot)

## Useful Links

[Docker+Nginx+SSL](https://mindsers.blog/post/https-using-nginx-certbot-docker/)

[Docker+Nginx+Certbot](https://pentacent.medium.com/nginx-and-lets-encrypt-with-docker-in-less-than-5-minutes-b4b8a60d3a71)

[Websocket+NGINX](https://www.nginx.com/blog/websocket-nginx/)

###
