cd EurekaServer
    sudo docker rm EurekaServer
    sudo docker rmi eureka-server
    sudo docker build -t eureka-server .
    sudo docker run -d -p 8761:8761 --net msnet --ip 172.18.0.254 --name EurekaServer eureka-server
cd ..

#################################
# webcrawler
cd webcrawler
    sudo docker rm WebCrawler1
    sudo docker rm WebCrawler2
    sudo docker rm WebCrawler3
    sudo docker rm WebCrawler4
    sudo docker rm WebCrawler5
    sudo docker rm WebCrawler6
    sudo docker rmi webcrawler
    sudo docker build -t webcrawler .
    sudo docker run --cpu-period=100000 --cpu-quota=50000 -d -p 8001:8080 --net msnet --ip 172.18.0.11 --name WebCrawler1 webcrawler
    sudo docker run --cpu-period=100000 --cpu-quota=50000 -d -p 8002:8080 --net msnet --ip 172.18.0.12 --name WebCrawler2 webcrawler
    sudo docker run --cpu-period=100000 --cpu-quota=50000 -d -p 8003:8080 --net msnet --ip 172.18.0.13 --name WebCrawler3 webcrawler
    sudo docker run --cpu-period=100000 --cpu-quota=50000 -d -p 8004:8080 --net msnet --ip 172.18.0.14 --name WebCrawler4 webcrawler
    sudo docker run --cpu-period=100000 --cpu-quota=50000 -d -p 8005:8080 --net msnet --ip 172.18.0.15 --name WebCrawler5 webcrawler
    sudo docker run --cpu-period=100000 --cpu-quota=50000 -d -p 8006:8080 --net msnet --ip 172.18.0.16 --name WebCrawler6 webcrawler
cd ..

cd servicestresser
    sudo docker rm ServiceStresser
    sudo docker rmi servicestresser
    sudo docker build -t servicestresser .
    sudo docker run -d -p 8090:8090 --net msnet --ip 172.18.0.200 --name ServiceStresser servicestresser
cd ..