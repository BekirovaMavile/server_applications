import threading
import time

counter = 0
lock = threading.Lock()

def increment_sync():
    global counter
    for _ in range(100000):
        with lock:
            local_counter = counter
            local_counter += 1
            counter = local_counter

def decrement_sync():
    global counter
    for _ in range(100000):
        with lock:
            local_counter = counter
            local_counter -= 1
            counter = local_counter

def run_threads_sync(n, m):
    global counter
    counter = 0
    threads = []

    for _ in range(n):
        t = threading.Thread(target=increment_sync)
        threads.append(t)
        t.start()

    for _ in range(m):
        t = threading.Thread(target=decrement_sync)
        threads.append(t)
        t.start()

    for t in threads:
        t.join()

start_time = time.time()
run_threads_sync(10, 10)
end_time = time.time()

print(f"Значение счетчика (с lock): {counter}")
print(f"Время выполнения: {end_time - start_time} секунд")
