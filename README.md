# FLW Planspiel

Dies ist das offizielle Repository für das neue FLW Planspiel.

## Setup

### Setup

1. SSL Server aus Nginx conf nehmen
2. Server starten mit ```docker-compose up```
3. ```docker-compose run --rm certbot certonly --webroot --webroot-path /var/www/certbot/ -d example.org```
4. SSL Server wieder hinzufügen

### Zertifikaterneuerung

```docker compose run --rm certbot renew```

### Hilfreiche Links

[Docker+Nginx+SSL](https://mindsers.blog/post/https-using-nginx-certbot-docker/)

[Docker+Nginx+Certbot](https://pentacent.medium.com/nginx-and-lets-encrypt-with-docker-in-less-than-5-minutes-b4b8a60d3a71)

[Websocket+NGINX](https://www.nginx.com/blog/websocket-nginx/)

###
