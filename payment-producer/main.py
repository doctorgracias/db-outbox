import requests
import random
import time

names = ["Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Henry"]

def random_payment():
    sender   = random.choice(names)
    receiver = random.choice([n for n in names if n != sender])
    return {
        "amount":     random.randint(10, 10000),
        "sender":     sender,
        "receiver":   receiver,
        "created_at": "2024-01-15T10:30:00Z"  # ← добавь это
    }

while True:
    payment = random_payment()
    try:
        response = requests.post(
            "http://127.0.0.1:8080/payment",
            json=payment
        )
        print(f"✓ {payment['sender']} → {payment['receiver']} : {payment['amount']} | status: {response.status_code}")
    except Exception as e:
        print(f"✗ Error: {e}")

    time.sleep(5)