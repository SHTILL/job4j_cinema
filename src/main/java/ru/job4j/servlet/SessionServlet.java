package ru.job4j.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.Schedule;
import ru.job4j.model.SessionDate;
import ru.job4j.store.PsqlStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String dateParam = req.getParameter("date");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        SessionDate date = gson.fromJson(dateParam, SessionDate.class);
        Schedule s = new Schedule(PsqlStore.getInstance().findSessions(date.getDate()));
        PrintWriter w = resp.getWriter();
        w.print(gson.toJson(s));
        w.flush();
    }
}
