FROM nginx:alpine

#!/bin/sh

COPY ./nginx/nginx.conf /etc/nginx/nginx.conf
COPY nginx/demoapp.conf /etc/nginx/sites-enabled/demoapp.conf
COPY nginx/rgenerator.conf /etc/nginx/sites-enabled/rgenerator.conf
COPY nginx/rgenerator-api.conf /etc/nginx/sites-enabled/rgenerator-api.conf
COPY nginx/keynote.conf /etc/nginx/sites-enabled/keynote.conf
COPY nginx/keynote-api.conf /etc/nginx/sites-enabled/keynote-api.conf

## Remove default nginx index page
RUN rm -rf /usr/share/nginx/html/*

EXPOSE 80 443

ENTRYPOINT ["nginx", "-g", "daemon off;"]