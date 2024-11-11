package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        var fileName = fileNameProvider.getTestFileName();
        List<QuestionDto> questions;
        try (var stream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (stream == null) {
                throw new QuestionReadException("File not found");
            }
            questions = new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(stream))
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .withSeparator(';')
                    .build().parse();
        } catch (IOException e) {
            throw new QuestionReadException("Questions reading failed", e);
        }
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        return questions.stream()
                .map(QuestionDto::toDomainObject)
                .toList();
    }
}
