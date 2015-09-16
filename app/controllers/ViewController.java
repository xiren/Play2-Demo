package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class ViewController extends Controller{

    public Result index(){
        return ok(index.render());
    }
    
    public Result getQuestion(){
        return ok(main.render());
    }
}
