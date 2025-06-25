package ru.mentee.power.collections.map;

import java.util.*;

/**
 * Класс для работы с конфигурационными данными в формате "ключ=значение"
 */
public class ConfigParser {

    private Map<String, String> configMap;
    Map<String, String> map = new HashMap<>();

    /**
     * Создает пустой объект ConfigParser
     */
    public ConfigParser() {
        configMap = new HashMap<>();
    }

    /**
     * Парсит строку конфигурации в формате "ключ=значение"
     * Каждая строка должна быть на отдельной строке.
     * Строки, начинающиеся с # считаются комментариями и игнорируются
     * Пустые строки игнорируются
     *
     * @param configString строка конфигурации
     * @throws IllegalArgumentException если строка конфигурации null
     */
    public void parseConfig(String configString) {
        if (configString == null){
            throw new IllegalArgumentException("Строка не может быть null");
        }
        // Разбиваем по строкам
        String[] lines = configString.split("\\r?\\n");

        for (String line : lines){
            line = line.trim();

            if (line.isEmpty() || line.contains("#")){
                continue;
            }

            String[] parts = line.split("=", 2);
            if(parts.length == 2){
                String key = parts[0];
                String value = parts[1];
                configMap.put(key,value);
            }
        }
    }

    /**
     * Преобразует текущую конфигурацию в строку
     *
     * @return строка конфигурации в формате "ключ=значение" с разделителями новой строки
     */
    public String toConfigString() {
      StringBuilder builder = new StringBuilder();
       for (Map.Entry<String, String> entry : configMap.entrySet()){
           builder.append(entry.getKey())
                   .append("=")
                   .append(entry.getValue())
                   .append("\n");

       }
        return builder.toString();
    }

    /**
     * Получает значение по ключу
     *
     * @param key ключ
     * @return значение или null, если ключ не найден
     * @throws IllegalArgumentException если ключ null
     */
    public String getValue(String key) {
        if(key == null){
            throw new IllegalArgumentException("Key can not be null");
        }
        return configMap.get(key);
    }

    /**
     * Получает значение по ключу или значение по умолчанию, если ключ не найден
     *
     * @param key ключ
     * @param defaultValue значение по умолчанию
     * @return значение или defaultValue, если ключ не найден
     * @throws IllegalArgumentException если ключ null
     */
    public String getValue(String key, String defaultValue) {
        if(key == null){
            throw new IllegalArgumentException("Key cannot be null");
        }
        return configMap.getOrDefault(key, defaultValue);
    }

    /**
     * Устанавливает значение для ключа
     *
     * @param key ключ
     * @param value значение
     * @return предыдущее значение или null, если ключ не существовал
     * @throws IllegalArgumentException если ключ или значение null
     */
    public String setValue(String key, String value) {
        if (key == null || value == null){
            throw new IllegalArgumentException("Incorrect parameters");
        }
        return  configMap.put(key, value);
    }

    /**
     * Удаляет ключ и его значение
     *
     * @param key ключ
     * @return true, если ключ существовал и был удален
     */
    public boolean removeKey(String key) {
        if ( key == null){
            throw new IllegalArgumentException("Ключ не может быть null");
        }
        if(configMap.containsKey(key)){
            configMap.remove(key);
            return true;
        }

        return false;
    }

    /**
     * Проверяет наличие ключа
     *
     * @param key ключ
     * @return true, если ключ существует
     */
    public boolean containsKey(String key) {
        return configMap.containsKey(key);
    }

    /**
     * Возвращает все ключи
     *
     * @return множество ключей
     */
    public Set<String> getKeys() {
        return  configMap.keySet();
    }

    /**
     * Возвращает все пары ключ-значение
     *
     * @return карта с парами ключ-значение
     */
    public Map<String, String> getAll() {
        return new HashMap<>(configMap);
    }

    /**
     * Получает целочисленное значение по ключу
     *
     * @param key ключ
     * @return целое число
     * @throws NoSuchElementException если ключ не найден
     * @throws NumberFormatException если значение не является числом
     */
    public int getIntValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        String value = configMap.get(key);
        if (value == null) {
            throw new NoSuchElementException("Key '" + key + "' not found");
        }

        return Integer.parseInt(value);
    }

    /**
     * Получает логическое значение по ключу
     * Строки "true", "yes", "1" (игнорируя регистр) считаются true
     * Все остальные значения считаются false
     *
     * @param key ключ
     * @return логическое значение
     * @throws NoSuchElementException если ключ не найден
     */
    public boolean getBooleanValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (!configMap.containsKey(key)) {
            throw new NoSuchElementException("Key '" + key + "' not found");
        }
        String value = configMap.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Null в значении не допустим");
        }
        String lowerValue = value.toLowerCase();
        return lowerValue.equals("true") || lowerValue.equals("yes") || lowerValue.equals("1");
    }

    /**
     * Получает список значений, разделенных запятыми
     *
     * @param key ключ
     * @return список значений или пустой список, если ключ не найден
     */
    public List<String> getListValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        String value = configMap.get(key);
        if (value == null){
            return Collections.emptyList();
        }
        String[] part = value.split(",");
        List<String> result = new ArrayList<>();
        for (String s : part){
            result.add(s.trim());
        }
        return result;
    }

    /**
     * Очищает все настройки
     */
    public void clear() {
       configMap.clear();
    }

    /**
     * Возвращает количество пар ключ-значение
     *
     * @return количество пар
     */
    public int size() {
        return configMap.size();
    }
}