./docker-build.sh
./docker-push.sh
cd ../frontend
./docker-run.sh
cd ../suite
docker-compose pull --parallel