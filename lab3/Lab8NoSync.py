import threading
import time

counter = 0

ITERATIONS = 100000

def increment():
    global counter
    for _ in range(ITERATIONS):
        local_counter = counter
        local_counter += 1
        counter = local_counter

def decrement():
    global counter
    for _ in range(ITERATIONS):
        local_counter = counter
        local_counter -= 1
        counter = local_counter

def run_threads(n, m):
    threads = []
    for _ in range(n):
        t = threading.Thread(target=increment)
        t.start()
        threads.append(t)

    for _ in range(m):
        t = threading.Thread(target=decrement)
        t.start()
        threads.append(t)

    for t in threads:
        t.join()

if __name__ == "__main__":
    n = 6  # Количество инкрементирующих потоков
    m = 3  # Количество декрементирующих потоков

    start_time = time.time()
    run_threads(n, m)
    end_time = time.time()

    print(f"Финальное значение счетчика: {counter}")
    print(f"Время выполнения: {end_time - start_time:.4f} секунд")
