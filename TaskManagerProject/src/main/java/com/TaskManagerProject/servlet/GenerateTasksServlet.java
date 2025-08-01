
package com.TaskManagerProject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

//@WebServlet("/GenerateTasksServlet")
public class GenerateTasksServlet extends HttpServlet {

    private static final String API_KEY = "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";
    private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");
        int teamSize = Integer.parseInt(request.getParameter("teamSize"));

        // Improved prompt for nested tasks and subtasks
        String prompt = "Divide the project '" + projectName + "' into " + teamSize + " team member responsibilities. "
                + "For each member, give a title and list 2-3 main tasks. For each main task, add 2-4 subtasks. "
                + "Format the response cleanly with this structure:\n"
                + "👤 Team Member X – Role\n"
                + "Main Task 1\n"
                + "- Subtask A\n"
                + "- Subtask B\n"
                + "Main Task 2\n"
                + "- Subtask A\n"
                + "- Subtask B";

        String requestBody = """
        {
          "model": "openai/gpt-3.5-turbo",
          "messages": [
            {"role": "user", "content": "%s"}
          ]
        }
        """.formatted(prompt);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(OPENROUTER_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .header("Referer", "http://localhost")
                .header("X-Title", "TaskManagerApp")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> apiResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JSONObject obj = new JSONObject(apiResponse.body());

            String aiMessage = obj.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            // Prepare HTML output
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<html><head><title>Project Tasks</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; padding: 20px; }");
            out.println("h2 { color: #2c3e50; }");
            out.println("ul { list-style-type: none; padding-left: 20px; }");
            out.println("input[type=checkbox] { margin-right: 8px; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<h2>✅ Generated Task Checklist for: " + projectName + "</h2>");

            // Parsing AI message into nested format
            String[] lines = aiMessage.split("\\r?\\n");
            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("👤")) {
                    out.println("<h3>" + line + "</h3><ul>");
                } else if (!line.isEmpty() && !line.startsWith("-")) {
                    out.println("<li><strong>" + line + "</strong><ul>");
                } else if (line.startsWith("-")) {
                    String subtask = line.replaceFirst("-\\s*", "");
                    out.println("<li><input type='checkbox'>" + subtask + "</li>");
                }
                // Close lists if next header starts
                if (line.isEmpty()) {
                    out.println("</ul></li></ul>");
                }
            }

            out.println("</body></html>");
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3>Error while calling OpenRouter:</h3>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            out.println("</body></html>");
            out.close();
        }
    }
}