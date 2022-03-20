# Архитектура игры Hollow Rogue

## Список разработчиков

- Абрамов Никита
- Венедиктов Роман
- Филиппов Денис

## Общие сведения о системе
### Назначение

Hollow Rogue -- это игра в жанре roguelike с тайловой графикой.
Предназначена для развлечения.

### Границы системы
- Тайловая графика
- Управление производится с помощью клавиатуры
- Управление не настраиваемое
- Интерфейс отображается на экране целиком
- Карта представляет собой поле в декартовой системе координат
- Каждая локация (уровень) могут генерироваться независимо или задаваться через специальный файл

### Контекст системы

Игра используется в качестве развлекательного контента для большой группы лиц. Предполагается поддержка на платформах Linux и Mac OS

## Architecture drivers

### Технические ограничения
- Для выбранного языка программирования должна быть библиотека для работы с тайловой графикой

### Бизнес ограничения
- Команда разработчиков состоит из трех человек
- Необходимо показывать промежуточный функционал заказчику каждые 1-2 недели
- Приложение должно быть готовым в начале мая

### Качественные характеристики системы
- Важна расширяемость приложения
- Важна возможность произвольного расширения функционала по требованию заказчика
- Производительность не важна

### Основные функциональные требования
- Тайловая графика
- Персонаж игрока, способный перемещаться по карте, управляемый с клавиатуры
- Персонаж должен взаимодействовать с локацией, npc и предметами в инвентаре
- У персонажа должны быть характеристики
- Карта обычно генерируется, но для некоторых уровней грузится из файла

## Роли и случаи использования

### Основные роли
- Игрок
- Дизайнер уровней

### Случаи использования
- Игрок хочет разнообразия в игре, поэтому уровни должны генерироваться случайно и на них должно быть много предметов и npc
- Игрок хочет иметь возможность развиваться в игре по-разному, поэтому должно быть много комбинаций предметов
- Дизайнер уровней не умеет программировать, поэтому у него должна быть возможность без написания кода задать свой уровень