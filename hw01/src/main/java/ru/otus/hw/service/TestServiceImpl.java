package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        var questions = questionDao.findAll();
        for (int i = 0; i < questions.size(); i++) {
            var question = questions.get(i);
            ioService.printFormattedLine("%d. %s",i + 1, question.text());
            var answers = question.answers();
            for (int j = 0; j < answers.size(); j++) {
                var answer = answers.get(j);
                ioService.printFormattedLine("\u0009 %d) %s", j + 1, answer.text());
            }
            ioService.printLine("");
        }
    }
}
