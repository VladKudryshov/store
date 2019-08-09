#sudo docker run --name db -e POSTGRES_PASSWORD=1 -p 5432:5432 -d postgres:9.6 || docker start db

#FTP
#docker run --name server-ftp -d -p 21:21 -p 21100-21110:21100-21110 -v /opt/server/nginx/html/files:/home/vsftpd/reports -e FTP_USER=reports -e FTP_PASS=1 -e LOG_STDOUT=true -e PASV_ADDRESS=172.17.255.255 -e PASV_MIN_PORT=21100 -e PASV_MAX_PORT=21110 -e FILE_OPEN_MODE=0755 -e LOCAL_UMASK=002 --restart=always fauria/vsftpd || docker restart server-ftp

cd ~/../home/projects/store
git pull
sudo docker stop platform
sudo docker rm -f platform
sudo docker build -t platform .
sudo docker run --name platform -d -p 8080:8080 -v /opt/server/nginx/html/files:/opt/server/temp platform




