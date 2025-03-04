package webhttp.controllers;

import webhttp.Message;
import webhttp.library.Controller;
import webhttp.library.Response;

public class MessageController extends Controller {
    public Object index() {
        return Response.ok(new Message("Hello, World!"));
    }

    public Object store() {
        return Response.created(new Message("Hello, World!"));
    }
}