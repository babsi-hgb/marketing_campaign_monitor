#!/bin/bash

# Build and start

#################################
# eureka
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
    sudo docker rmi webcrawler
    sudo docker build -t webcrawler .
    sudo docker run -d -p 8001:8080 --net msnet --ip 172.18.0.11 --name WebCrawler1 webcrawler
    sudo docker run -d -p 8002:8080 --net msnet --ip 172.18.0.12 --name WebCrawler2 webcrawler
cd ..

#################################
# analytics
cd analytics
    sudo docker rm Analytics1
    sudo docker rm Analytics2
    sudo docker rm Analytics3
    sudo docker rm Analytics4
    sudo docker rmi analytics
    sudo docker build -t analytics .
    sudo docker run -d -p 8011:8080 --net msnet --ip 172.18.0.21  --name Analytics1 analytics
    #sudo docker run -d -p 8012:8080 --net msnet --ip 172.18.0.22  --name Analytics2 analytics
    #sudo docker run --cpu-period=100000 --cpu-quota=50000 -d -p 8013:8080 --net msnet --ip 172.18.0.23  --name Analytics3 analytics
    #sudo docker run --cpu-period=100000 --cpu-quota=50000 -d -p 8014:8080 --net msnet --ip 172.18.0.24  --name Analytics4 analytics
cd ..

#################################
# gateway
cd gateway
    sudo docker rm Gateway
    sudo docker rmi gateway
    sudo docker build -t gateway .
    sudo docker run -d -p 8080:8080 --net msnet --ip 172.18.0.100 --name Gateway gateway
cd ..



