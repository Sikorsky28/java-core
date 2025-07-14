package ru.mentee.power.tdd.notes;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;


public class Note  {


    private final int id; // Уникальный ID
    private String title; // Заголовок
    private String text; // Текст заметки
    private final LocalDate creationDate; // Дата создания
    private Set<String> tags; // Набор тегов (уникальные строки)

    // Принимает id, title, text.
    // Устанавливает creationDate текущей датой (LocalDate.now()).
    // Инициализирует tags пустым HashSet.
    // Проверяет title и text на null (можно бросать IllegalArgumentException или использовать значение по умолчанию).
    public Note(int id, String title, String text){

        if (title == null){
            this.title = "title";
        }else {
            this.title = title;
        }
        if (text == null){
            this.text = "text";
        }else {
            this.text = text;
        }
        this.id = id;
        this.creationDate = LocalDate.now();
        this.tags = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }



    public void setTitle(String title) {
        if (title == null){
            throw new IllegalArgumentException("Title cannot be null");
        }
        this.title = title;
    }

    public void setText(String text) {
        if (text == null){
            throw new IllegalArgumentException("Text cannot be null");
        }
        this.text = text;
    }

    // Добавляет тег в множество tags.
    // Тег должен быть не null и не пустым. Привести к нижнему регистру перед добавлением.
    public void addTag(String tag) {
        if(tag == null || tag.isBlank()){
            throw new IllegalArgumentException("Tag cannot be null or blank");
        }
        tags.add(tag.toLowerCase());
    }

    // Удаляет тег из множества (сравнение без учета регистра).
    public boolean removeTag(String tag) {
        if (tag == null || tag.isBlank()) {
            throw new IllegalArgumentException("Tag cannot be null or blank");
        }
        return tags.remove(tag.toLowerCase());
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}