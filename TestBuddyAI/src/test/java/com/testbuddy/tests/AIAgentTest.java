package com.testbuddy.tests;

import org.testng.annotations.Test;

import com.testbuddy.ai.AIAgent;

public class AIAgentTest {

    @Test
    public void testAIConnection() throws Exception {
        AIAgent agent = new AIAgent();
        String response = agent.askAI("Say 'Hello, TestBuddyAI is connected!' in one line.");
        System.out.println("AI Response: " + response);
    }
}