package com.hackathon.unpack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Questionnaire extends AppCompatActivity {

    private ArrayList<Question> generalQuestions;
    private ArrayList<Question>[] specificQuestions;
    private ArrayList<Question> currentQuestions;
    private int index = 0;
    private final int numDisorders = getResources().getStringArray(R.array.disorders).length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        currentQuestions = new ArrayList<>();
        setUpQuestions();
        currentQuestions.addAll(generalQuestions);

        setNextQuestion(null);
    }

    private void setUpQuestions(){
        //Set up General Questions
        generalQuestions = new ArrayList<>();


        specificQuestions = new ArrayList[numDisorders];
        int i = 0;
        //Set up questions for Depression
        specificQuestions[i++] = new ArrayList<>();

        //Set up questions for Anxiety
        specificQuestions[i++] = new ArrayList<>();

        //Set up questions for Eating Disorders
        specificQuestions[i++] = new ArrayList<>();

        //Set up questions for ADHD
        specificQuestions[i++] = new ArrayList<>();

        //Set up questions for PTSD
        specificQuestions[i++] = new ArrayList<>();

        //Set up questions for Substance Abuse
        specificQuestions[i++] = new ArrayList<>();

        //Set up questions for Bipolar Disorders
        specificQuestions[i++] = new ArrayList<>();

        //Set up questions for Schizophrenia
        specificQuestions[i++] = new ArrayList<>();
    }

    public void setNextQuestion(View view){

    }

    private class Question{

        private String question;
        private String[] options;
        private int[][] optionScores;

        public Question(String question, String[] options, int[][] optionScores){
            this.question = question;
            this.options = options;
            this.optionScores = optionScores;

            if(this.optionScores.length != this.options.length ||
                    this.optionScores[0].length != numDisorders)
                throw new RuntimeException("Formatting for questions built incorrectly. Exiting.");
        }

        public String getQuestion(){
            return question;
        }

        public String[] getOptions(){
            return options;
        }

        public int[][] getOptionScores(){
            return optionScores;
        }
    }
}