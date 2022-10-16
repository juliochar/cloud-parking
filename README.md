# cloud-parking

## Rodar Banco De dados Postgresql via docker

docker run --name parking-db -p 5433:5432 -e POSTGRES_DB=parking -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123 -d postgres:10-alpine

usando porta 5433 por já usar postgres na máquina, não esquecer de configurar corretamente para o JPA
***


