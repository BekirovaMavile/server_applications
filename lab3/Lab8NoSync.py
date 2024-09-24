import threading
import time

# Инициализация счетчика
counter = 0

# Количество итераций
ITERATIONS = 100000

def increment():
    global counter
    for _ in range(ITERATIONS):
        # Чтение и инкрементирование счетчика
        local_counter = counter
        local_counter += 1
        counter = local_counter

def decrement():
    global counter
    for _ in range(ITERATIONS):
        # Чтение и декрементирование счетчика
        local_counter = counter
        local_counter -= 1
        counter = local_counter

def run_threads(n, m):
    threads = []
    
    # Создание и запуск потоков инкрементирования
    for _ in range(n):
        t = threading.Thread(target=increment)
        t.start()
        threads.append(t)
    
    # Создание и запуск потоков декрементирования
    for _ in range(m):
        t = threading.Thread(target=decrement)
        t.start()
        threads.append(t)
    
    # Ожидание завершения всех потоков
    for t in threads:
        t.join()

# Запуск программы
if __name__ == "__main__":
    n = 6  # Количество инкрементирующих потоков
    m = 3  # Количество декрементирующих потоков

    start_time = time.time()
    run_threads(n, m)
    end_time = time.time()

    print(f"Финальное значение счетчика: {counter}")
    print(f"Время выполнения: {end_time - start_time:.4f} секунд")
