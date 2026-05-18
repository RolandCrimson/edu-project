docker run --name pgvector -d -p 5432:5432 `
  -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e TZ=Asia/Seoul `
  -v pg-volume:/var/lib/postgresql/data `
  pgvector/pgvector:pg17 postgres -c max_connections=500