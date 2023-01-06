FROM nginx:alpine

#!/bin/sh

COPY ./nginx/nginx.conf /etc/nginx/nginx.conf
COPY nginx/demoapp.conf /etc/nginx/sites-enabled/demoapp.conf

## Remove default nginx index page
RUN rm -rf /usr/share/nginx/html/*

EXPOSE 80 443

ENTRYPOINT ["nginx", "-g", "daemon off;"]