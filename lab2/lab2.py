import json

class Appliance:
    def __init__(self, name, power, is_on=False):
        self.name = name 
        self.power = power 
        self.is_on = is_on  

    def turn_on(self):
        """Включить прибор."""
        self.is_on = True

    def turn_off(self):
        """Выключить прибор."""
        self.is_on = False

    def __str__(self):
        return f"{self.name} (Мощность: {self.power}Вт, Включен: {self.is_on})"


# Кухонные приборы
class KitchenAppliance(Appliance):
    def __init__(self, name, power):
        super().__init__(name, power)


# Развлекательные приборы
class EntertainmentAppliance(Appliance):
    def __init__(self, name, power):
        super().__init__(name, power)


# Приборы уборки
class CleaningAppliance(Appliance):
    def __init__(self, name, power):
        super().__init__(name, power)


class ApplianceManager:
    def __init__(self):
        self.appliances = []

    def add_appliance(self, appliance):
        """Добавить электроприбор."""
        self.appliances.append(appliance)

    def total_power(self):
        """Подсчитать общую мощность включенных приборов."""
        return sum(a.power for a in self.appliances if a.is_on)

    def sort_by_power(self):
        """Сортировать приборы по мощности."""
        return sorted(self.appliances, key=lambda a: a.power)

    def find_in_power_range(self, min_power, max_power):
        """Найти приборы, соответствующие диапазону мощности."""
        return [a for a in self.appliances if min_power <= a.power <= max_power]

    def load_from_file(self, filename):
        """Загрузить параметры приборов из файла."""
        with open(filename, 'r') as file:
            data = json.load(file)
            for item in data:
                if item['type'] == 'kitchen':
                    appliance = KitchenAppliance(item['name'], item['power'])
                elif item['type'] == 'entertainment':
                    appliance = EntertainmentAppliance(item['name'], item['power'])
                elif item['type'] == 'cleaning':
                    appliance = CleaningAppliance(item['name'], item['power'])
                else:
                    continue
                if item['is_on']:
                    appliance.turn_on()
                self.add_appliance(appliance)

    def save_to_file(self, filename):
        """Сохранить параметры приборов в файл."""
        with open(filename, 'w') as file:
            data = []
            for a in self.appliances:
                item = {
                    'name': a.name,
                    'power': a.power,
                    'is_on': a.is_on,
                    'type': type(a).__name__.replace('Appliance', '').lower()
                }
                data.append(item)
            json.dump(data, file, indent=4)

def main():
    manager = ApplianceManager()

    manager.add_appliance(KitchenAppliance("Холодильник", 150))
    manager.add_appliance(EntertainmentAppliance("Телевизор", 100))
    manager.add_appliance(CleaningAppliance("Пылесос", 200))

    manager.appliances[0].turn_on()  # холодильник
    manager.appliances[2].turn_on()  # пылесос

    print(f"Общая потребляемая мощность: {manager.total_power()}Вт")

    print("\nПриборы, отсортированные по мощности:")
    for appliance in manager.sort_by_power():
        print(appliance)

    min_power = 100
    max_power = 200
    print(f"\nПриборы в диапазоне мощности {min_power}-{max_power}Вт:")
    for appliance in manager.find_in_power_range(min_power, max_power):
        print(appliance)

    manager.save_to_file('appliances.json')

    print("\nЗагрузка приборов из файла:")
    manager.load_from_file('appliances.json')
    for appliance in manager.appliances:
        print(appliance)

if __name__ == "__main__":
    main()