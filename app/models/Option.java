package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="t_option")
public class Option extends Model{

    @Id
    private int id;
    private String  content;
    private long count = 0;
    @ManyToOne
    private Question question;
    
    public static Finder<Integer, Option> find = new Finder<>(Option.class);
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @JsonIgnore
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((question == null) ? 0 : question.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Option other = (Option) obj;
        if (id != other.id)
            return false;
        if (question == null) {
            if (other.question != null)
                return false;
        } else if (!question.equals(other.question))
            return false;
        return true;
    }
}
