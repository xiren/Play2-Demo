package controllers;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;

import models.Option;
import models.Question;
import models.QuestionResponse;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class QuestionController extends Controller {

    public Result getQuestion() {
        int rowCount = Question.find.findRowCount();
        if (rowCount != 0) {
            Random r = new Random();
            List<Question> questions = Question.find.fetch("options").findList();
            return ok(Json.toJson(buildResponse("return successfully", questions.get(r.nextInt(rowCount)))));
        } else {
            return ok(Json.toJson(buildResponse("no question.")));
        }
    }

    public Result answer() {
        JsonNode requestBody = request().body().asJson();
        int questionId = requestBody.get("questionId").asInt();
        int optionId = requestBody.get("optionId").asInt();

        List<Option> options = Option.find.where().eq("question_id", questionId).findList();
        Optional<Option> optionalOption = options.stream().filter(r -> r.getId() == optionId).findAny();
        if (!optionalOption.isPresent()) {
            return badRequest(Json.toJson(buildResponse("option is invalid.")));
        }
        Option option = optionalOption.get();
        option.setCount(option.getCount() + 1);
        option.save();

        long count = 0;
        Optional<Long> optionalCount = options.stream().map(r -> r.getCount()).reduce((x, y) -> x + y);

        if (optionalCount.isPresent()) {
            count = optionalCount.get();
        }

        double rate = (option.getCount() * 1.0) / (count * 1.0) * 100.0;
        BigDecimal normal = new BigDecimal(50);
        BigDecimal decimal = new BigDecimal(rate);
        decimal = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        String result = "";
        if (decimal.compareTo(normal) >= 0) {
            result = decimal.toString() + "% , Congratulation, you are normal.";
        } else {
            result = decimal.toString() + "% , Sorry, you are moon-calf.";
        }
        return ok(Json.toJson(buildResponse(result)));
    }

    @BodyParser.Of(BodyParser.Json.class)
    @Transactional
    public Result addQuestion() {
        JsonNode requestBody = request().body().asJson();
        String title = requestBody.get("title").asText();
        String content = requestBody.get("content").asText();
        JsonNode options = requestBody.get("options");
        Question question = new Question();
        question.setContent(content);
        question.setTitle(title);
        question.save();
        Iterator<JsonNode> it = options.elements();
        while (it.hasNext()) {
            JsonNode op = it.next();
            Option option = new Option();
            option.setQuestion(question);
            option.setContent(op.get("content").asText());
            option.save();
        }

        return ok(Json.toJson(buildResponse("save successfully")));
    }

    @Transactional
    public Result deleteQuestion(int id) {
        Question question = Question.find.byId(id);
        if (question != null) {
            question.delete();
            return ok(Json.toJson(buildResponse("delete successfully")));
        } else {
            return badRequest(Json.toJson(buildResponse("id doesn't exist.")));
        }
    }

    @Transactional
    public Result updateQuestion(int id) {
        JsonNode requestBody = request().body().asJson();
        JsonNode title = requestBody.get("title");
        JsonNode content = requestBody.get("content");
        JsonNode options = requestBody.get("options");
        Question question = Question.find.byId(id);
        if (question != null) {
            question.setTitle(title.asText());
            question.setContent(content.asText());
            List<Option> optionList = question.getOptions();
            Iterator<JsonNode> it = options.elements();
            while (it.hasNext()) {
                JsonNode op = it.next();
                int optionId = op.get("id").asInt();
                for (Option p : optionList) {
                    if (p.getId() == optionId) {
                        p.setContent(op.get("content").asText());
                    }
                }
            }
            question.save();
        }
        return ok(Json.toJson(buildResponse("update successfully")));
    }

    public Result getQuestionById(int id) {
        Question question = Question.find.fetch("options").where().eq("id", id).findUnique();
        return ok(Json.toJson(buildResponse("return successfully", question)));
    }

    public Result getAllQuestions() {
        List<Question> questions = Question.find.fetch("options").findList();
        return ok(Json.toJson(buildResponse("return successfully", questions)));
    }

    private QuestionResponse buildResponse(String msg, List<Question> questions) {
        QuestionResponse response = new QuestionResponse();
        response.setMessage(msg);
        response.setQuestion(questions);
        return response;
    }

    private QuestionResponse buildResponse(String msg, Question question) {
        QuestionResponse response = new QuestionResponse();
        response.setMessage(msg);
        response.addQuestion(question);
        return response;
    }

    private QuestionResponse buildResponse(String msg) {
        QuestionResponse response = new QuestionResponse();
        response.setMessage(msg);
        return response;
    }
}
