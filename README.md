# Betting
Demo Betting App

# Technology Stack
Java: 11 <br />
SpringBoot: 2.7.17 <br />
Postgres: 15.4 <br />

# Git
Fetch one of the following branches (main/master/staging), all in synch with latest commit.

# How to compile (without enabling unit tests)
docker build -t betting .

# How to compile (with unit tests enabled)
1) start locally a postgres container <br />
docker run --name some-postgres -e POSTGRES_PASSWORD=password -e POSTGRES_USER=root -e POSTGRES_DB=bet -p 5432:5432 -d postgres:15.4

2) get the internal IP of the running container, using the inspect docker command <br />
docker inspect some-postgres

3) build the image locally setting the DATABASE_URL with the internal IP of the postgres container <br />
docker build -t betting --build-arg SKIPTESTS=false --build-arg POSTGRES_DB=bet --build-arg POSTGRES_USER=root --build-arg POSTGRES_PASSWORD=password --build-arg DATABASE_URL=172.17.0.2 .

Remember to stop and prune all running containers before starting the Demo Betting App <br />
i) docker container stop some-postgres <br />
ii) docker container prune

#How to run <br />
docker compose up


# Swagger
http://localhost:8080/swagger-ui/index.html