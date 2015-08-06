package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.Model;

@Entity
@Table(name="t_question")
public class Question extends Model{
    @Id
    private int id;
    private String title;
    private String content;
    
    @OneToMany(mappedBy="question", cascade=CascadeType.ALL)
    private List<Option> options;
    
    public static Finder<Integer, Question> find = new Finder<>(Question.class);
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public List<Option> getOptions() {
        return options;
    }
    public void setOptions(List<Option> options) {
        this.options = options;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        Question other = (Question) obj;
        if (id != other.id)
            return false;
        return true;
    }
    
}
