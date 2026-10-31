# Transaction Outbox Pattern — Learning Project

A minimal implementation of the Transactional Outbox pattern built for learning purposes.

## What it does

A payment service that accepts HTTP requests and writes to two tables in a single transaction: the main `payment` table and the `payment_outbox` table. A separate poller reads unprocessed outbox records and forwards them to Kafka (stub for now), then marks them as sent.

## Stack

- Scala 2 + ZIO 
- Tapir + Netty 
- Slick 
- PostgreSQL
- Python 

## Why Outbox

Writing to a database and a message broker in two separate operations is not atomic. If the app crashes between them, data becomes inconsistent. The Outbox pattern solves this by writing both the business data and the event to the database in a single transaction. A separate process then reliably forwards the event to the broker.

## Run

```bash
# start postgres
# run payment-service
# run kafka-producer
# run load generator
python script.py
```