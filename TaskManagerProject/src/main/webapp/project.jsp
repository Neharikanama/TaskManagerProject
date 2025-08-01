<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Submit Project</title>
<style>
    body {
        background: linear-gradient(120deg, #a18cd1, #fbc2eb);
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .form-box {
        background-color: #ffffff;
        padding: 40px;
        border-radius: 20px;
        width: 420px;
        box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
    }

    .form-box h2 {
        font-size: 28px;
        text-align: center;
        font-weight: bold;
        color: #333333;
        margin-bottom: 30px;
    }

    .form-group {
        margin-bottom: 20px;
    }

    label {
        font-weight: 600;
        font-size: 14px;
        color: #555;
        margin-bottom: 8px;
        display: block;
    }

    input, select, textarea {
        width: 100%;
        padding: 14px 18px;
        font-size: 15px;
        border: 1px solid #ddd;
        border-radius: 12px;
        outline: none;
        background-color: #fafafa;
        transition: border 0.2s;
    }

    input:focus, select:focus, textarea:focus {
        border-color: #a18cd1;
        background-color: #fff;
    }

    .submit-btn {
        background: linear-gradient(to right, #a18cd1, #fbc2eb);
        color: white;
        font-weight: bold;
        padding: 14px;
        border: none;
        width: 100%;
        border-radius: 30px;
        font-size: 16px;
        cursor: pointer;
        transition: background 0.3s ease;
    }

    .submit-btn:hover {
        background: linear-gradient(to right, #8e8ee9, #f88fd0);
    }
</style>
</head>
<body>
   <!-- ✅ Move form tag to wrap all fields -->
    <form action="GenerateTasksServlet" method="post" class="form-box">
        <h2>Create Project</h2>

        <div class="form-group">
            <label for="projectName">Project Name</label>
            <input type="text" id="projectName" name="projectName" placeholder="Enter project name" required>
        </div>

        <div class="form-group">
            <label for="description">Project Description</label>
            <textarea id="description" name="description" rows="3" placeholder="Brief description..." required></textarea>
        </div>

        <div class="form-group">
            <label for="teamSize">Team Size</label>
            <input type="number" id="teamSize" name="teamSize" placeholder="Enter team size" min="1" required>
        </div>

        <div class="form-group">
            <label for="deadline">Deadline</label>
            <input type="date" id="deadline" name="deadline" required>
        </div>

        <button type="submit" class="submit-btn">Submit</button>
    </form>
</body>
</html>
   
