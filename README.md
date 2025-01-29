# FLW Planspiel / FLW Businessgame

This is the official repository for the new FLW Businessgame [Link](http://log-bg.mb.tu-dortmund.de/).

This repository includes the 
- Dolores Backend, a spring boot application that implements the business logic of the game
- Dolores Frontend, a vue frontend that serves as access point for user and admins

## Setup

### Normal Setup

1. Clone repository
2. Adapt nginx.conf if necessary
3. Run dolores with ```docker-compose up```

### Setup with certbot

1. Clone repository
2. Remove SSL configuration from nginx.conf
3. Start server with ```docker-compose up```
4. Run certbot ```docker-compose run --rm certbot certonly --webroot --webroot-path /var/www/certbot/ -d example.org```
5. Re-add SSL configuration in nginx.conf

### Certificate renewal

Simply run
```docker compose run --rm certbot renew```

### Useful Links

[Docker+Nginx+SSL](https://mindsers.blog/post/https-using-nginx-certbot-docker/)

[Docker+Nginx+Certbot](https://pentacent.medium.com/nginx-and-lets-encrypt-with-docker-in-less-than-5-minutes-b4b8a60d3a71)

[Websocket+NGINX](https://www.nginx.com/blog/websocket-nginx/)

###
