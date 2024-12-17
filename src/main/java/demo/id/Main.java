package demo.id;

import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class Main {
    @GetMapping("/start")
    public Map<String, Object> start(HttpSession httpSession) {
        // Initialize session attributes
        Set<Integer> usedNumbers = new HashSet<>();
        httpSession.setAttribute("usedNumbers", usedNumbers);
        
        // Use a list to track results for each question
        List<Map<String, Object>> questionResults = new ArrayList<>();
        httpSession.setAttribute("questionResults", questionResults);

        Random random = new Random();

        // Generate a unique random number
        int randomNumber;
        synchronized (usedNumbers) {
            do {
                randomNumber = random.nextInt(5) + 1; // Range: 1 to 5
            } while (usedNumbers.contains(randomNumber));

            usedNumbers.add(randomNumber);
        }

        // Store randomNumber in session
        httpSession.setAttribute("currentRandomNumber", randomNumber);

        // Call db class 
        db obj = new db();
        obj.get(randomNumber);

        // Build response
        Map<String, Object> response = new HashMap<>();
        response.put("Next Question", obj.questionMap);

        return response;
    }

    @GetMapping("/submit/{ans}")
    public Map<String, Object> submit(@PathVariable String ans, HttpSession httpSession) {
        // Retrieve session attributes
        Set<Integer> usedNumbers = (Set<Integer>) httpSession.getAttribute("usedNumbers");
        Integer currentRandomNumber = (Integer) httpSession.getAttribute("currentRandomNumber");
        
        // Retrieve or initialize question results list
        List<Map<String, Object>> questionResults = 
            (List<Map<String, Object>>) httpSession.getAttribute("questionResults");
        if (questionResults == null) {
            questionResults = new ArrayList<>();
            httpSession.setAttribute("questionResults", questionResults);
        }

        // Ensure session attributes exist
        if (usedNumbers == null) {
            usedNumbers = new HashSet<>();
            httpSession.setAttribute("usedNumbers", usedNumbers);
        }

        // Process answer and get result for current question
        db obj = new db();
        obj.ans(currentRandomNumber, ans);

        // Add current question's result to the results list
        Map<String, Object> currentQuestionResult = new HashMap<>();
        currentQuestionResult.put("QuestionNumber", currentRandomNumber);
        currentQuestionResult.put("Result", obj.questionMap);
        questionResults.add(currentQuestionResult);

        // Check if all questions have been used
        if (usedNumbers.size() == 5) {
            // Build final response with all question results
            Map<String, Object> finalResponse = new HashMap<>();
            finalResponse.put("AllQuestionResults", questionResults);
            
            // Reset session
            httpSession.invalidate();
            return finalResponse;
        }

        // Generate next random number
        Random random = new Random();
        int randomNumber;
        synchronized (usedNumbers) {
            do {
                randomNumber = random.nextInt(5) + 1; // Range: 1 to 5
            } while (usedNumbers.contains(randomNumber));

            usedNumbers.add(randomNumber);
        }

        // Store new random number in session
        httpSession.setAttribute("currentRandomNumber", randomNumber);

        // Call db class for next question
        obj.get(randomNumber);

        // Build response with current results and next question
        Map<String, Object> response = new HashMap<>();
        //response.put("CurrentQuestionResult", currentQuestionResult);
        response.put("NextQuestion", obj.questionMap);

        return response;
    }
}