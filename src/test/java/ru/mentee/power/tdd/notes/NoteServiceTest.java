package ru.mentee.power.tdd.notes;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты для NoteService")
class NoteServiceTest {

    private NoteService noteService;

    @BeforeEach
    void setUp() {
        noteService = new NoteService();
    }

    @Nested
    @DisplayName("Тесты добавления заметок")
    class AddNoteTests {
        @Test
        @DisplayName("Добавление валидной заметки")
        void shouldAddValidNote() {
            // Arrange
            String title = "Первая заметка";
            String text = "Текст первой заметки";
            Set<String> tags = Set.of("java", "тест");
            // Act
            Note addedNote = noteService.addNote(title, text, tags);
            // Assert
            assertThat(addedNote).isNotNull();
            assertThat(addedNote.getId()).isGreaterThan(0);
            assertThat(addedNote.getTitle()).isEqualTo(title);
            assertThat(addedNote.getTags()).containsExactly("java", "тест");
            assertThat(noteService.getAllNotes()).containsExactly(addedNote);
        }

        @Test
        @DisplayName("Добавление заметки с null title/text")
        void shouldHandleNullTitleAndText() {
            // Arrange
            // Act
            Note addedNote = noteService.addNote(null, null, null);
            // Assert
            assertThat(addedNote.getText()).isEqualTo("text");
            assertThat(addedNote.getTitle()).isEqualTo("title");
            assertThat(addedNote.getTags()).isEmpty();
        }

        @Test
        @DisplayName("Нельзя добавить заметку с пустым тегом")
        void shouldNotAllowEmptyTag() {
            Set<String> tags = Set.of("", "products", "list");

            assertThatThrownBy(() -> noteService.addNote(
                    "Список покупок",
                    "Колбаса, сыр, майонез, зеленый горошек",
                    tags
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Tag cannot be null or blank");
        }

        @Test
        @DisplayName("Нельзя добавить заметку, если среди тегов есть null")
        void shouldThrowIfTagIsNull() {
            Set<String> tags = new HashSet<>(Arrays.asList("shopping", null, "list"));

            assertThatThrownBy(() -> noteService.addNote(
                    "Покупки",
                    "Молоко, яйца, хлеб",
                    tags
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Tag cannot be null or blank");
        }


        @Nested
        @DisplayName("Тесты получения заметок")
        class GetNoteTests {
            private Note note1, note2, note3, note4;
            private Set<String> tags, tagsM, tagsT, tagsW;

            @BeforeEach
            void setUp() {
                tags = new HashSet<>(Arrays.asList("general", "home", "dayOf"));
                tagsM = new HashSet<>(Arrays.asList("work", "nature", "learning"));
                tagsT = new HashSet<>(Arrays.asList("general", "Escribimos", "enjoy"));
                tagsW = new HashSet<>(Arrays.asList("genial", "home", "pensar sobre las cosas buenas"));

                note1 = noteService.addNote("Список дел на воскресенье", "Стирка, учеба, готовка, уборка.", tags);
                note2 = noteService.addNote("Список дел на понедельник", "тренировка, учеба, готовка, отдых.", tagsM);
                note3 = noteService.addNote("Список дел на вторник", "Сон, учеба, прогулка, уборка.", tagsT);
                note4 = noteService.addNote("Список дел на среду", "готовка, учеба, хобби, стирка.", tagsW);
            }

            @Test
            @DisplayName("Должен успешно находить заметку по id")
            void shouldFindNoteById() {
                Optional<Note> result = noteService.getNoteById(note2.getId());
                assertThat(result).isPresent();
                assertThat(result.get().getTitle()).isEqualTo("Список дел на понедельник");
            }

            @Test
            @DisplayName("Должен возвращать Optional.empty() при попытке найти несуществующую заметку")
            void shouldReturnEmptyIfNoteNotFound() {
                assertThat(noteService.getNoteById(999)).isEmpty();
            }

            @Test
            @DisplayName("Должен возвращать все добавленные заметки")
            void shouldReturnAllNotes() {
                List<Note> allNotes = noteService.getAllNotes();
                assertThat(allNotes).containsExactlyInAnyOrder(note1, note2, note3, note4);
            }

            @Test
            @DisplayName("Должен возвращать пустой список, если заметок нет")
            void shouldReturnEmptyListIfNoNotes() {
                NoteService emptyService = new NoteService();
                assertThat(emptyService.getAllNotes()).isEmpty();
            }
        }


        @Nested
        @DisplayName("Тесты обновления заметок")
        class UpdateNoteTests {

            @Test
            @DisplayName("Обновление текста существующей заметки")
            void shouldUpdateNoteText() {
                Note note = noteService.addNote("Test", "Old text", Set.of("tag"));
                boolean result = noteService.updateNoteText(note.getId(), "New text", "new");

                assertThat(result).isTrue();
                assertThat(noteService.getNoteById(note.getId()).get().getText()).isEqualTo("new");
            }

            @Test
            @DisplayName("Обновление текста несуществующей заметки")
            void shouldNotUpdateNonexistentNote() {
                boolean result = noteService.updateNoteText(999, "New text", "vii");
                assertThat(result).isFalse();
            }

            @Test
            @DisplayName("Добавление нового тега к заметке")
            void shouldAddNewTagToNote() {
                Note note = noteService.addNote("Title", "Text", Set.of("old"));
                boolean result = noteService.addTagToNote(note.getId(), "new");

                assertThat(result).isTrue();
                assertThat(noteService.getNoteById(note.getId()).get().getTags()).contains("old", "new");
            }

            @Test
            @DisplayName("Добавление существующего тега к заметке")
            void shouldNotDuplicateExistingTag() {
                Note note = noteService.addNote("Title", "Text", Set.of("same"));
                boolean result = noteService.addTagToNote(note.getId(), "same");

                assertThat(result).isFalse();
            }

            @Test
            @DisplayName("Добавление тега к несуществующей заметке")
            void shouldFailAddTagToNonexistentNote() {
                boolean result = noteService.addTagToNote(999, "tag");
                assertThat(result).isFalse();
            }

            @Test
            @DisplayName("Удаление существующего тега")
            void shouldRemoveTagFromNote() {
                Note note = noteService.addNote("Title", "Text", Set.of("a", "b"));
                boolean result = noteService.removeTagFromNote(note.getId(), "a");

                assertThat(result).isTrue();
                assertThat(noteService.getNoteById(note.getId()).get().getTags()).doesNotContain("a");
            }

            @Test
            @DisplayName("Удаление несуществующего тега")
            void shouldNotRemoveNonexistentTag() {
                Note note = noteService.addNote("Title", "Text", Set.of("only"));
                boolean result = noteService.removeTagFromNote(note.getId(), "missing");

                assertThat(result).isFalse();
            }

            @Test
            @DisplayName("Удаление тега у несуществующей заметки")
            void shouldFailRemoveTagFromNonexistentNote() {
                boolean result = noteService.removeTagFromNote(999, "tag");
                assertThat(result).isFalse();
            }
        }

            @Nested
            @DisplayName("Тесты удаления заметок")
            class DeleteNoteTests {
                @Test
                @DisplayName("Удаление существующей заметки")
                void shouldDeleteNoteById() {
                    Note note = noteService.addNote("Удалить", "Удалить", Set.of("x"));
                    boolean deleted = noteService.deleteNote(note.getId());

                    assertThat(deleted).isTrue();
                    assertThat(noteService.getNoteById(note.getId())).isEmpty();
                }
                @Test
                @DisplayName("Попытка удалить несуществующую заметку")
                void shouldNotDeleteNonexistentNote() {
                    boolean deleted = noteService.deleteNote(999);
                    assertThat(deleted).isFalse();
                }
            }

            @Nested
            @DisplayName("Тесты поиска заметок")
            class FindNoteTests {
                @Test
                @DisplayName("Поиск по существующему тексту")
                void shouldFindByExactText() {
                    Note note = noteService.addNote("Title", "find me", Set.of("tag"));
                    List<Note> found = noteService.findNotesByText("find me");

                    assertThat(found).contains(note);
                }

                @Test
                @DisplayName("Поиск по части текста")
                void shouldFindByPartialText() {
                    Note note = noteService.addNote("Title", "something to find", Set.of("tag"));
                    List<Note> found = noteService.findNotesByText("to find");

                    assertThat(found).contains(note);
                }

                @Test
                @DisplayName("Поиск по тексту в разном регистре")
                void shouldFindByTextIgnoreCase() {
                    Note note = noteService.addNote("Title", "MiXeD CaSe", Set.of("tag"));
                    List<Note> found = noteService.findNotesByText("mixed case");

                    assertThat(found).contains(note);
                }

                @Test
                @DisplayName("Поиск по несуществующему тексту")
                void shouldReturnEmptyIfTextNotFound() {
                    noteService.addNote("Title", "Some text", Set.of("tag"));
                    List<Note> found = noteService.findNotesByText("missing");

                    assertThat(found).isEmpty();
                }

                @Test
                @DisplayName("Поиск по одному тегу")
                void shouldFindBySingleTag() {
                    Note note = noteService.addNote("Title", "Text", Set.of("urgent"));
                    List<Note> found = noteService.findNotesByTags(Set.of("urgent"));

                    assertThat(found).contains(note);
                }

                @Test
                @DisplayName("Поиск по нескольким тегам")
                void shouldFindByMultipleTags() {
                    Note note = noteService.addNote("Title", "Text", Set.of("a", "b"));
                    List<Note> found = noteService.findNotesByTags(Set.of("a", "b"));

                    assertThat(found).contains(note);
                }

                @Test
                @DisplayName("Поиск с частично несуществующими тегами")
                void shouldFindIfSomeTagsExist() {
                    Note note = noteService.addNote("Title", "Text", Set.of("real"));
                    List<Note> found = noteService.findNotesByTags(Set.of("real", "fake"));

                    assertThat(found).contains(note);
                }

                @Test
                @DisplayName("Поиск по несуществующим тегам")
                void shouldReturnEmptyForNonexistentTags() {
                    List<Note> found = noteService.findNotesByTags(Set.of("ghost"));
                    assertThat(found).isEmpty();
                }

                @Test
                @DisplayName("Поиск с пустым набором тегов")
                void shouldReturnEmptyForEmptyTagSet() {
                    List<Note> found = noteService.findNotesByTags(Set.of());
                    assertThat(found).isEmpty();
                }
            }

            @Nested
            @DisplayName("Тесты работы с тегами")
            class TagTests {
                @Test
                @DisplayName("Возврат пустого списка тегов, если заметок нет")
                void shouldReturnEmptyTagsIfNoNotes() {
                    NoteService emptyService = new NoteService();
                    Set<String> tags = emptyService.getAllTags();

                    assertThat(tags).isEmpty();
                }

                @Test
                @DisplayName("Возврат всех уникальных тегов из заметок")
                void shouldReturnAllUniqueTags() {
                    noteService.addNote("One", "Text", Set.of("a", "b"));
                    noteService.addNote("Two", "Text", Set.of("b", "c"));

                    Set<String> tags = noteService.getAllTags();
                    assertThat(tags).containsExactlyInAnyOrder("a", "b", "c");
                }
            }
    }
}