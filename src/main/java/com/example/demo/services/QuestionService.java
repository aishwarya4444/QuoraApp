package com.example.demo.services;

import com.example.demo.adapter.QuestionAdapter;
import com.example.demo.dto.QuestionRequestDTO;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.models.Question;
import com.example.demo.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO requestDto) {
        Question question = Question.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return questionRepository.save(question)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnSuccess((response -> System.out.println("Question created: " + response)))
                .doOnError((error -> System.out.println("Error creating question: " + error.getMessage())));

//        Mono<Question> questionMono = questionRepository.save(question);
//        Mono<QuestionResponseDTO> responseDto = questionMono.map(QuestionAdapter::toQuestionResponseDTO);

//        return Mono.just(QuestionAdapter.toQuestionResponseDTO(question));
    }

    @Override
    public Mono<QuestionResponseDTO> getQuestion(String id) {
        return questionRepository.findById(id)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnSuccess((response -> System.out.println("Question retrieved: " + response)))
                .doOnError((error -> System.out.println("Error retrieving question: " + error)));
    }
}
