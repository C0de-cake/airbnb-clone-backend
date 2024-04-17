services:
  postgres:
    image: 'postgres:latest'
    volumes:
      - ~/volumes/codecake/postgresql/:/var/lib/postgresql/data
    environment:
      - POSTGRES_PASSWORD=codecake
      - POSTGRES_USER=codecake
    ports:
      - 127.0.0.1:5432:5432
