package ru.job4j.servlet;

import com.google.gson.Gson;
import ru.job4j.model.Hall;
import ru.job4j.model.HallManager;
import ru.job4j.model.Session;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HallServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sessionParam = req.getParameter("session");
        Gson gson = new Gson();
        Session session = gson.fromJson(sessionParam, Session.class);
        HallManager manager = HallManager.getInstance();
        Hall h = manager.loadHall(session);
        PrintWriter w = resp.getWriter();
        w.print(new Gson().toJson(h));
        w.flush();
    }
}
