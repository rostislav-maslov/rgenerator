docker build -t mtech-proxy ./
docker image tag mtech-proxy:latest pocteg/mtech-proxy:latest
docker push pocteg/mtech-proxy