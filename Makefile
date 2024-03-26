
MVN = mvn

all: test

test:
	$(MVN) test

unit-test:
	$(MVN) test -P unit

integration-test:
	$(MVN) test -P integration


run:
	mvn clean package -DskipTests
	docker-compose -f docker/docker-compose.yml up --force-recreate --build

down:
	docker-compose -f docker/docker-compose.yml down

