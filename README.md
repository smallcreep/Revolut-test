# Revolut-test

# Запуск тестов

Тесты можно запустит командой:

``./gradlew clean check -PQuality -PIntegration -PhubUrl=Appium_hub_url -PappPath=Path_to_application``

В файле `src/main/resources/settings.ini` можоно найти настройки создаваемого драйвера.
Они задаются с именем и значением которые передаются в hub как есть.

# TODO
* Unit-tests;
* Разделить элеметы по типам (button, checkbox, select etc.);
* Пересмотреть архитектуру, возможно перекомпоновать;
* Добавить элементы в xml;
* Скомпоновать шаги в "бизнес шаги";