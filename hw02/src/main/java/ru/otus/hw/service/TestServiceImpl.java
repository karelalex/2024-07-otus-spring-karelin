package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            ioService.printLine(question.text());
            var answers = question.answers();
            for (int j = 0; j < answers.size(); j++) {
                var answer = answers.get(j);
                ioService.printFormattedLine("\u0009 %d) %s", j + 1, answer.text());
            }
            var answerNumber = ioService.readIntForRangeWithPrompt(
                    1,
                    question.answers().size(),
                    "Выберите ответ",
                    "Нужно ввести корректный номер ответа");
            var isAnswerValid = answers.get(answerNumber - 1).isCorrect(); // Задать вопрос, получить ответ
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
