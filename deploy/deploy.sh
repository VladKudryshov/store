sudo docker run --name order-db -e POSTGRES_PASSWORD=1 -p 5432:5432 -d postgres:9.6 || docker start order-db

sudo docker rm -f order-back
sudo docker build -t order-back .
sudo docker run --name order-back -d -p 8080:8080 order-back