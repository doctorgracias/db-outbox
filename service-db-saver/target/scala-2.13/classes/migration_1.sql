CREATE TABLE IF NOT EXISTS payment (
	id SERIAL PRIMARY KEY,
	amount INT NOT NULL,
	sender VARCHAR(50),
	receiver VARCHAR(50) NOT NULL,
	created_at TIMESTAMP
);
CREATE TABLE IF NOT EXISTS payment_outbox (
	id SERIAL PRIMARY KEY,
	payment_id INT REFERENCES payment(id) NOT NULL,
	amount INT NOT NULL,
	sender VARCHAR(50),
	receiver VARCHAR(50) NOT NULL,
	created_at TIMESTAMP,
	sent_at TIMESTAMP
);