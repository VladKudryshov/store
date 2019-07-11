
#UI & Nginx
#docker build --rm -f Dockerfile -t server-nginx .
#docker run --name server-nginx -d -p 80:80 -v /opt/server/nginx/nginx.conf:/etc/nginx/nginx.conf -v /opt/server/nginx/html:/usr/share/nginx/html -v /opt/server/nginx/logs:/var/log/nginx/ server-nginx

cd ~/../home/projects/insta
git pull

docker stop platform
npm run build

rm -rf /opt/server/nginx/html/
mkdir /opt/server/nginx/html
mv build/* /opt/server/nginx/html
docker restart server-nginx
docker start platform





