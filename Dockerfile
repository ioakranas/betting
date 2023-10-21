FROM ubuntu:20.04

MAINTAINER  Ioannis Kranas <ioakranas@gmail.com>


RUN apt-get update && apt-get -y install software-properties-common \
 && add-apt-repository ppa:linuxuprising/java \
 && apt-get update \
 && apt-get -y install screen iputils-ping netcat vim vsftpd net-tools \
 openssh-server virtualenv telnet apt-transport-https openjdk-11-jdk curl maven \
 && mkdir /betting

COPY . /betting

ARG DATABASE_URL
ARG POSTGRES_DB
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD
ARG SKIPTESTS=true

ENV POSTGRES_DB=$POSTGRES_DB
ENV POSTGRES_USER=$POSTGRES_USER
ENV POSTGRES_PASSWORD=$POSTGRES_PASSWORD
ENV SKIPTESTS=$SKIPTESTS
ENV DATABASE_URL=$DATABASE_URL

RUN cd /betting \
  && mvn clean package -DskipTests=$SKIPTESTS

EXPOSE 8080

WORKDIR /betting
  
CMD cd /betting \
  && java -jar target/betting-0.0.1-SNAPSHOT.jar \
  && sleep infinity