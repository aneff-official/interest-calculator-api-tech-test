.PHONY: run smoke

run:
	docker compose up --build

smoke:
	docker compose up -d --build
	sleep 10
	curl -f http://localhost:8080/actuator/health || (docker compose logs app && exit 1)
	docker compose down
