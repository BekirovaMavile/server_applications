import threading
import time

counter = 0
rlock = threading.RLock()

def increment_reentrant():
    global counter
    for _ in range(100000):
        with rlock:
            local_counter = counter
            local_counter += 1
            counter = local_counter

def decrement_reentrant():
    global counter
    for _ in range(100000):
        with rlock:
            local_counter = counter
            local_counter -= 1
            counter = local_counter

def run_threads_reentrant(n, m):
    global counter
    counter = 0
    threads = []

    for _ in range(n):
        t = threading.Thread(target=increment_reentrant)
        threads.append(t)
        t.start()

    for _ in range(m):
        t = threading.Thread(target=decrement_reentrant)
        threads.append(t)
        t.start()

    for t in threads:
        t.join()

start_time = time.time()
run_threads_reentrant(10, 10)
end_time = time.time()

print(f"Значение счетчика (с RLock): {counter}")
print(f"Время выполнения: {end_time - start_time} секунд")


def test_performance():
    with open("Lab8.txt", "w") as f:
        f.write("Threads (n=m)\tCounter\tTime (s)\n")
        for num_threads in [1, 2, 4, 8]:
            start_time = time.time()
            run_threads_reentrant(num_threads, num_threads)
            end_time = time.time()
            f.write(f"{num_threads}\t\t{counter}\t\t{end_time - start_time}\n")

test_performance()
