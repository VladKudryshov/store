sudo docker run --name order-db -e POSTGRES_PASSWORD=1 -p 5432:5432 -d postgres:9.6 || docker start order-db


#UI & Nginx
#docker build --rm -f Dockerfile -t server-nginx .
#docker run --name server-nginx -d -p 80:80 -v /opt/server/nginx/nginx.conf:/etc/nginx/nginx.conf -v /opt/server/nginx/html:/usr/share/nginx/html -v /opt/server/nginx/logs:/var/log/nginx/ server-nginx

#FTP
#docker run --name server-ftp -d -p 21:21 -p 21100-21110:21100-21110 -v /opt/server/nginx/html/files:/home/vsftpd/reports -e FTP_USER=reports -e FTP_PASS=1 -e LOG_STDOUT=true -e PASV_ADDRESS=172.17.255.255 -e PASV_MIN_PORT=21100 -e PASV_MAX_PORT=21110 -e FILE_OPEN_MODE=0755 -e LOCAL_UMASK=002 --restart=always fauria/vsftpd || docker restart server-ftp

cd
cd ..
cd ../home/kudr9tov/projects/store
git pull
sudo docker stop order-back
sudo docker rm -f order-back
sudo docker build -t order-back .
sudo docker run --name order-back -d -p 8080:8080 order-back


cd
cd ..
cd ../home/kudr9tov/projects/insta
git pull
npm run build

rm -rf /opt/server/nginx/html
mv build/* /opt/server/nginx/html

