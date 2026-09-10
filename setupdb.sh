#!/bin/bash

docker run \
  --name postgres-db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=SpringBootLogin \
  -p 5433:5432 \
  -v postgres_data:/var/lib/postgresql \
  -d postgres:latest

until docker exec postgres-db pg_isready -U postgres > /dev/null 2>&1
do
  sleep 1
done

docker exec -i postgres-db \
  psql -U postgres -d SpringBootLogin < schema.sql
