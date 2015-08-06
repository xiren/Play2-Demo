package models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionResponse {

    String message;
    List<Question> question ;
    
    @JsonProperty(value="msg")
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    @JsonInclude(Include.NON_NULL)
    @JsonProperty(value="data")
    public List<Question> getQuestion() {
        return question;
    }
    public void setQuestion(List<Question> question) {
        this.question = question;
    }
    public void addQuestion(Question question){
        if(this.question == null){
            this.question = new ArrayList<>();
        }
        this.question.add(question);
    }
    
}
