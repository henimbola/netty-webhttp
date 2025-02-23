package webhttp.controllers;

import webhttp.library.Controller;
import java.util.Map;

public class MessageController extends Controller {
    public Object index() {
        return Map.of(
            "message", "List of posts",
            "status", 200
        );
    }
}