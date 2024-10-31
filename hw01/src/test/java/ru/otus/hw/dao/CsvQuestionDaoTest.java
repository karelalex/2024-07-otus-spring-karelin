package ru.otus.hw.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;

public class CsvQuestionDaoTest {

    private CsvQuestionDao subj;
    private TestFileNameProvider provider;
    @BeforeEach
    void initialize() {
        provider = mock(TestFileNameProvider.class);
        subj = new CsvQuestionDao(provider);
    }

    @Test
    @DisplayName("Дао корректно загружает вопросы при указании правильного имени файла")
    public void correctLoading() {
        when(provider.getTestFileName()).thenReturn("questions.csv");
        var questions = subj.findAll();
        assertThat(questions).hasSize(3);
    }

    @Test
    @DisplayName("Дао выбрасывает исключение, если путь к файлу с вопросами некорректный")
    public void incorrectPath(){
        when(provider.getTestFileName()).thenReturn("voprosy.csv");
        assertThatThrownBy(() -> subj.findAll())
                .isInstanceOf(QuestionReadException.class)
                .hasMessage("File not found");
    }
}
