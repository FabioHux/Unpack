package com.hackathon.unpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Questionnaire extends AppCompatActivity {

    private ArrayList<Question> generalQuestions;
    private ArrayList<Question>[] specificQuestions;
    private ArrayList<Question> currentQuestions;
    private int index = 0, selectedIndex;
    private int numDisorders;
    private int[] scores, buttonIDs;

    private final int THRESHOLD = 10;

    private RadioGroup optiongroup;
    private TextView question_title;
    private Button[] disorders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        optiongroup = findViewById(R.id.options);
        question_title = findViewById(R.id.question_tv);
        numDisorders = getResources().getStringArray(R.array.disorders).length;
        disorders = new Button[]{findViewById(R.id.disorder_one), findViewById(R.id.disorder_two), findViewById(R.id.disorder_three)};

        currentQuestions = new ArrayList<>();
        setUpQuestions();
        currentQuestions.addAll(generalQuestions);

        scores = new int[numDisorders];
        buildQuestion(currentQuestions.get(index));
        selectedIndex = -1;
    }

    private void setUpQuestions(){
        //Set up General Questions
        generalQuestions = new ArrayList<>();
        generalQuestions.add(new Question(
                "In General when experiencing a mental health episode which one of the following best describes it?",
                new String[]{
                        "Pressure in the chest and is usually accompanied by increased heart rate.",
                        "It's never an episode, just something that always seems to be there or comes and goes in waves.",
                        "The Episode can be tied to a physical thing or an event.",
                        "I don't think it's a problem. I have been told it is one.",
                        "None of the Above"
                },
                new double[][]{
                        new double[]{2,5,0,0,0,0,0,0},
                        new double[]{0,0,0,0,0,2,5,0},
                        new double[]{0,0,2,0,5,0,0,0},
                        new double[]{2,0,0,0,0,0,0,0},
                        new double[]{0,0,0,0,0,0,0,0}
                }
        ));
        generalQuestions.add(new Question(
                "In the last two weeks have you felt?",
                new String[]{
                        "Nervous or on edge",
                        "Worried",
                        "Little Interest in things",
                        "Hopeless",
                        "Mood Swings",
                        "None of the Above"
                },
                new double[][]{
                        new double[]{1,5,0,2,0,0,0,0},
                        new double[]{0,5,0,0,0,0,0,0},
                        new double[]{5,0,0,3,0,3,0,3},
                        new double[]{5,0,0,0,0,0,0,0},
                        new double[]{0,0,5,0,0,0,0,0},
                        new double[]{0,0,0,0,0,0,0,0}
                }
        ));

        generalQuestions.add(new Question(
                "In the last two weeks, how often did you feel that way?",
                new String[]{
                        "only one",
                        "A couple days",
                        "more days than not",
                        "Everyday",
                        "None of the above"
                },
                new double[][]{
                        new double[]{1,1,1,1,1,1,1,1},
                        new double[]{1.1,1.1,1.1,1.1,1.1,1.1,1.1,1.1},
                        new double[]{1.2,1.2,1.2,1.2,1.2,1.2,1.2,1.2},
                        new double[]{1.3,1.3,1.3,1.3,1.3,1.3,1.3,1.3},
                        new double[]{0,0,0,0,0,0,0,0}

                },
                generalQuestions.get(generalQuestions.size()-1)
        ));
        generalQuestions.add(new Question(
                "How are you most likely to start your day?",
                new String[]{
                        "Weighing yourself",
                        "Getting “Waked and Baked” or things like that?",
                        "Dreading getting out of bed.",
                        "Waking up in a cold sweat from a nightmare about something that happened in the past.",
                        "You feel like someone is watching you.",
                        "getting ready for the day ahead"

                },
                new double[][]{
                        new double[]{0,0,5,0,0,0,0,0},
                        new double[]{0,0,0,0,0,5,0,0},
                        new double[]{5,0,0,0,0,0,0,0},
                        new double[]{0,0,0,0,5,0,0,0},
                        new double[]{0,0,0,0,0,0,0,7},
                        new double[]{0,0,0,0,0,0,0,0}

                }
        ));
        //change weights for questions below
        generalQuestions.add(new Question(
                "How have your eating habits been?",
                new String[]{
                        "Eating less than usual",
                        "Normal",
                        "Eating A lot more than usual",
                        "I go through waves of not eating at all to overeating"
                },
                new double[][]{
                        new double[]{0,0,2,0,0,0,0,0},
                        new double[]{0,0,0,0,0,0,0,0},
                        new double[]{0,0,3,0,0,0,0,0},
                        new double[]{0,0,5,0,0,0,0,0}

                }
        ));
        generalQuestions.add(new Question(
                "How have the people around act?",
                new String[]{
                        "They tell you to stop drinking or doing drugs regularly.",
                        "You feel like they’re always watching you",
                        "You avoid people",
                        "People avoid you",
                        "None of the above."
                },
                new double[][]{
                        new double[]{0,0,0,0,0,5,0,0},
                        new double[]{0,0,0,0,0,0,0,5},
                        new double[]{5,0,0,0,0,0,0,0},
                        new double[]{0,0,0,0,3,3,0,0},
                        new double[]{0,0,0,0,0,0,0,0}

                }
        ));
        generalQuestions.add(new Question(
                "What are your views for the future?",
                new String[]{
                        "I'm hopeful for the future",
                        "Each day just feels like a chore",
                        "It changes from day to day",
                        "Drugs are the only thing that gets me through it"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,0,0},
                        new double[]{5,0,0,0,0,0,0,0},
                        new double[]{0,0,0,0,0,0,5,0},
                        new double[]{0,0,0,0,0,5,0,0}

                }
        ));
        generalQuestions.add(new Question(
                "How have your sleep patterns been",
                new String[]{
                        "Restless nights because of recurring nightmares",
                        "I don't really sleep",
                        "I can never fall asleep because something is always watching",
                        "Some nights I sleep a lot and then some night I can't seem to fall asleep.",
                        "I would rather just sleep all day",
                        "consistent every night"
                },
                new double[][]{
                        new double[]{0,0,0,0,5,0,0,0},
                        new double[]{2,5,0,0,0,0,0,0},
                        new double[]{0,0,0,0,0,0,0,5},
                        new double[]{5,0,0,0,0,0,0,0},
                        new double[]{3,0,0,0,0,0,0,0},
                        new double[]{0,0,0,0,0,0,0,0}

                }
        ));
        generalQuestions.add(new Question(
                "Do you ever have thoughts about hurting yourself?",
                new String[]{
                        "All the time",
                        "Sometimes",
                        "Only when I think about a certain event",
                        "Only when I gained or lost weight ",
                        "Never"
                },
                new double[][]{
                        new double[]{10,0,0,0,0,0,0,0},
                        new double[]{5,0,0,0,0,0,0,0},
                        new double[]{0,0,0,0,5,0,0,0},
                        new double[]{0,0,5,0,0,0,0,0},
                        new double[]{0,0,0,0,0,0,0,0}

                }
        ));
        generalQuestions.add(new Question(
                "Have you been treated for or diagnosed for one of the following:",
                new String[]{
                        "Depression",
                        "Anxiety",
                        "Eating Disorder",
                        "ADHD",
                        "PTSD",
                        "Substance Abuse",
                        "Bipolar Disorder",
                        "Schizophrenia",
                        "None of the above"
                },
                new double[][]{
                        new double[]{5,0,0,0,0,0,0,0},
                        new double[]{0,5,0,0,0,0,0,0},
                        new double[]{5,0,5,0,0,0,0,0},
                        new double[]{0,0,0,5,0,0,0,0},
                        new double[]{0,0,0,0,5,0,0,0},
                        new double[]{0,0,0,0,0,5,0,0},
                        new double[]{0,0,0,0,0,0,5,0},
                        new double[]{0,0,0,0,0,0,0,5},
                        new double[]{0,0,0,0,0,0,0,0,}

                }
        ));



        specificQuestions = new ArrayList[numDisorders];
        int i = 0;
        //Set up questions for Depression
        ArrayList<Question> temp = new ArrayList<>();
        specificQuestions[i++] = temp;
        temp.add(new Question(
                "Do you feel like you have little interest in doing things?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{4,0,0,0,0,0,0,0},
                        new double[]{2,0,0,0,0,0,0,0},
                        new double[]{-1,0,0,0,0,0,0,0},
                        new double[]{-2,0,0,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Are you often feeling down, depressed or hopeless in your day to day life?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{4,0,0,0,0,0,0,0},
                        new double[]{2,0,0,0,0,0,0,0},
                        new double[]{-1,0,0,0,0,0,0,0},
                        new double[]{-2,0,0,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you have the trouble falling asleep, or sleeping too much?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{4,0,0,0,0,0,0,0},
                        new double[]{2,0,0,0,0,0,0,0},
                        new double[]{-1,0,0,0,0,0,0,0},
                        new double[]{-2,0,0,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you have thoughts that you would be better off dead?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{4,0,0,0,0,0,0,0},
                        new double[]{2,0,0,0,0,0,0,0},
                        new double[]{-1,0,0,0,0,0,0,0},
                        new double[]{-2,0,0,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Are you often tired or have little energy?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{4,0,0,0,0,0,0,0},
                        new double[]{2,0,0,0,0,0,0,0},
                        new double[]{-1,0,0,0,0,0,0,0},
                        new double[]{-2,0,0,0,0,0,0,0}
                }

        ));

        //Set up questions for Anxiety
        temp = new ArrayList<>();
        specificQuestions[i++] = temp;
        temp.add(new Question(
                "Do you often Feel nervous, anxious or on edge?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,4,0,0,0,0,0,0},
                        new double[]{0,2,0,0,0,0,0,0},
                        new double[]{0,-1,0,0,0,0,0,0},
                        new double[]{0,-2,0,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Are you usually worrying about different things throughout the day?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,4,0,0,0,0,0,0},
                        new double[]{0,2,0,0,0,0,0,0},
                        new double[]{0,-1,0,0,0,0,0,0},
                        new double[]{0,-2,0,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Having trouble sitting still because you're restless?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,4,0,0,0,0,0,0},
                        new double[]{0,2,0,0,0,0,0,0},
                        new double[]{0,-1,0,0,0,0,0,0},
                        new double[]{0,-2,0,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Have you been easily annoyed or irritated?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,4,0,0,0,0,0,0},
                        new double[]{0,2,0,0,0,0,0,0},
                        new double[]{0,-1,0,0,0,0,0,0},
                        new double[]{0,-2,0,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Have a constant feeling that something bad might happen?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,4,0,0,0,0,0,0},
                        new double[]{0,2,0,0,0,0,0,0},
                        new double[]{0,-1,0,0,0,0,0,0},
                        new double[]{0,-2,0,0,0,0,0,0}
                }

        ));


        //Set up questions for Eating Disorders
        temp = new ArrayList<>();
        specificQuestions[i++] = temp;
        temp.add(new Question(
                "Do you force yourself to throw up if you feel full?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,4,0,0,0,0,0},
                        new double[]{0,0,2,0,0,0,0,0},
                        new double[]{0,0,-1,0,0,0,0,0},
                        new double[]{0,0,-2,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Does your life mainly focus on food or what you're going to eat?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,4,0,0,0,0,0},
                        new double[]{0,0,2,0,0,0,0,0},
                        new double[]{0,0,-1,0,0,0,0,0},
                        new double[]{0,0,-2,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you feel you can't control how much you eat?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,4,0,0,0,0,0},
                        new double[]{0,0,2,0,0,0,0,0},
                        new double[]{0,0,-1,0,0,0,0,0},
                        new double[]{0,0,-2,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you see yourself as fat and others think of you as skinny?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,4,0,0,0,0,0},
                        new double[]{0,0,2,0,0,0,0,0},
                        new double[]{0,0,-1,0,0,0,0,0},
                        new double[]{0,0,-2,0,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Have you gone many hours without eating food, even when you were hungry?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,4,0,0,0,0,0},
                        new double[]{0,0,2,0,0,0,0,0},
                        new double[]{0,0,-1,0,0,0,0,0},
                        new double[]{0,0,-2,0,0,0,0,0}
                }

        ));

        //Set up questions for ADHD
        temp = new ArrayList<>();
        specificQuestions[i++] = temp;
        temp.add(new Question(
                "Do you often find yourself delaying task that take up time or lots of thought?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,4,0,0,0,0},
                        new double[]{0,0,0,2,0,0,0,0},
                        new double[]{0,0,0,-1,0,0,0,0},
                        new double[]{0,0,0,-2,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you find yourself easily distracted by sounds or activities around you?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,4,0,0,0,0},
                        new double[]{0,0,0,2,0,0,0,0},
                        new double[]{0,0,0,-1,0,0,0,0},
                        new double[]{0,0,0,-2,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you often find yourself restless or fidgety?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,4,0,0,0,0},
                        new double[]{0,0,0,2,0,0,0,0},
                        new double[]{0,0,0,-1,0,0,0,0},
                        new double[]{0,0,0,-2,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you find yourself having trouble waiting your turn in a line?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,4,0,0,0,0},
                        new double[]{0,0,0,2,0,0,0,0},
                        new double[]{0,0,0,-1,0,0,0,0},
                        new double[]{0,0,0,-2,0,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you find yourself having the feeling of getting up when you have to sit for a long period of time?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,4,0,0,0,0},
                        new double[]{0,0,0,2,0,0,0,0},
                        new double[]{0,0,0,-1,0,0,0,0},
                        new double[]{0,0,0,-2,0,0,0,0}
                }

        ));
        //Set up questions for PTSD
        temp = new ArrayList<>();
        specificQuestions[i++] = temp;
        temp.add(new Question(
                "Do you find yourself fixating on an event from the past?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,4,0,0,0},
                        new double[]{0,0,0,0,2,0,0,0},
                        new double[]{0,0,0,0,-1,0,0,0},
                        new double[]{0,0,0,0,-2,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you find yourself avoiding things that remind you of that even?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,4,0,0,0},
                        new double[]{0,0,0,0,2,0,0,0},
                        new double[]{0,0,0,0,-1,0,0,0},
                        new double[]{0,0,0,0,-2,0,0,0}
                }

        ));
        temp.add(new Question(
                "Have you found yourself feeling overly guarded or are easily startled?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,4,0,0,0},
                        new double[]{0,0,0,0,2,0,0,0},
                        new double[]{0,0,0,0,-1,0,0,0},
                        new double[]{0,0,0,0,-2,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you find yourself feeling numb or detached from people?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,4,0,0,0},
                        new double[]{0,0,0,0,2,0,0,0},
                        new double[]{0,0,0,0,-1,0,0,0},
                        new double[]{0,0,0,0,-2,0,0,0}
                }

        ));
        temp.add(new Question(
                "Do you find yourself feeling guilty or unable to stop blaming yourself about an event that happened in the past?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,4,0,0,0},
                        new double[]{0,0,0,0,2,0,0,0},
                        new double[]{0,0,0,0,-1,0,0,0},
                        new double[]{0,0,0,0,-2,0,0,0}
                }

        ));
        //Set up questions for Substance Abuse
        temp = new ArrayList<>();
        specificQuestions[i++] = temp;
        temp.add(new Question(
                "Do you find yourself thinking about drugs or alcohol throughout the day?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,4,0,0},
                        new double[]{0,0,0,0,0,2,0,0},
                        new double[]{0,0,0,0,0,-1,0,0},
                        new double[]{0,0,0,0,0,-2,0,0}
                }

        ));
        temp.add(new Question(
                "Do you find yourself struggling not to consume drugs or alcohol?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,4,0,0},
                        new double[]{0,0,0,0,0,2,0,0},
                        new double[]{0,0,0,0,0,-1,0,0},
                        new double[]{0,0,0,0,0,-2,0,0}
                }

        ));
        temp.add(new Question(
                "Have you found yourself needing a larger dose to get the same high?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,4,0,0},
                        new double[]{0,0,0,0,0,2,0,0},
                        new double[]{0,0,0,0,0,-1,0,0},
                        new double[]{0,0,0,0,0,-2,0,0}
                }

        ));
        temp.add(new Question(
                "Have you ever skipped one of your responsibilities to consume drugs or alcohol?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,4,0,0},
                        new double[]{0,0,0,0,0,2,0,0},
                        new double[]{0,0,0,0,0,-1,0,0},
                        new double[]{0,0,0,0,0,-2,0,0}
                }

        ));
        temp.add(new Question(
                "Have you lost interest in some of your hobbies to take drugs or alcohol?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,4,0,0},
                        new double[]{0,0,0,0,0,2,0,0},
                        new double[]{0,0,0,0,0,-1,0,0},
                        new double[]{0,0,0,0,0,-2,0,0}
                }

        ));
        //Set up questions for Bipolar Disorders
        temp = new ArrayList<>();
        specificQuestions[i++] = temp;
        temp.add(new Question(
                "In some situations have you found yourself more talkative than usual?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,4,0},
                        new double[]{0,0,0,0,0,0,2,0},
                        new double[]{0,0,0,0,0,0,-1,0},
                        new double[]{0,0,0,0,0,0,-2,0}
                }

        ));
        temp.add(new Question(
                "Have you ever randomly felt irritated or jumpy?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,4,0},
                        new double[]{0,0,0,0,0,0,2,0},
                        new double[]{0,0,0,0,0,0,-1,0},
                        new double[]{0,0,0,0,0,0,-2,0}
                }

        ));
        temp.add(new Question(
                "Have you ever felt sad and happy at the same time?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,4,0},
                        new double[]{0,0,0,0,0,0,2,0},
                        new double[]{0,0,0,0,0,0,-1,0},
                        new double[]{0,0,0,0,0,0,-2,0}
                }

        ));
        temp.add(new Question(
                "Has your self confidence seem to change rapidly from time to time?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,4,0},
                        new double[]{0,0,0,0,0,0,2,0},
                        new double[]{0,0,0,0,0,0,-1,0},
                        new double[]{0,0,0,0,0,0,-2,0}
                }

        ));
        temp.add(new Question(
                " Have you ever felt dull then sharp at random times in the day?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,4,0},
                        new double[]{0,0,0,0,0,0,2,0},
                        new double[]{0,0,0,0,0,0,-1,0},
                        new double[]{0,0,0,0,0,0,-2,0}
                }

        ));
        //Set up questions for Schizophrenia
        temp = new ArrayList<>();
        specificQuestions[i++] = temp;
        temp.add(new Question(
                "Have you ever heard a voice or voices when no one was around?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,0,4},
                        new double[]{0,0,0,0,0,0,0,2},
                        new double[]{0,0,0,0,0,0,0,-1},
                        new double[]{0,0,0,0,0,0,0,-2}
                }

        ));
        temp.add(new Question(
                "Do you get the feeling that your thoughts were taken out of your head?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,0,4},
                        new double[]{0,0,0,0,0,0,0,2},
                        new double[]{0,0,0,0,0,0,0,-1},
                        new double[]{0,0,0,0,0,0,0,-2}
                }

        ));
        temp.add(new Question(
                "Do you get the feeling that others can read your thoughts, your thoughts are broadcasted to others?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,0,4},
                        new double[]{0,0,0,0,0,0,0,2},
                        new double[]{0,0,0,0,0,0,0,-1},
                        new double[]{0,0,0,0,0,0,0,-2}
                }

        ));
        temp.add(new Question(
                "Have you ever got the feeling that you were not incontrol of yourself, that someone was controlling you?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,0,4},
                        new double[]{0,0,0,0,0,0,0,2},
                        new double[]{0,0,0,0,0,0,0,-1},
                        new double[]{0,0,0,0,0,0,0,-2}
                }

        ));
        temp.add(new Question(
                "Do you get the feeling that someone is spying on you, or watching you?",
                new String[]{
                        "Strongly agree",
                        "Agree",
                        "Disagree",
                        "Strongly Disagree"
                },
                new double[][]{
                        new double[]{0,0,0,0,0,0,0,4},
                        new double[]{0,0,0,0,0,0,0,2},
                        new double[]{0,0,0,0,0,0,0,-1},
                        new double[]{0,0,0,0,0,0,0,-2}
                }

        ));
    }

    public void setNextQuestion(View view){
        if(selectedIndex >= 0){
            Log.i("DEPRESSION SCORE", "" + scores[0]);
            optiongroup.removeAllViews();

            double[] result =  currentQuestions.get(index).getOptionScores()[selectedIndex];

            int i;
            for(i = 0; i < result.length; i++){
                scores[i] += result[i];
            }

            index++;

            if(index == currentQuestions.size()){
                //Get top 3
                int[] topIndices = new int[]{-1,-1,-1};

                for(i = 0; i < scores.length; i++){
                    int j = 2;
                    while(j >= 0 && (topIndices[j] < 0 || scores[i] > scores[topIndices[j]])){
                        int temp = topIndices[j];
                        topIndices[j] = i;

                        if(j != 2){
                            topIndices[j + 1] = temp;
                        }
                        j--;
                    }
                }

                if(currentQuestions.get(0).equals(generalQuestions.get(0)) && scores[topIndices[0]] > THRESHOLD){
                    currentQuestions.clear();
                    for (int in : topIndices) {
                        if (scores[in] > THRESHOLD) {
                            currentQuestions.addAll(specificQuestions[in]);
                        }
                    }
                    index = 0;
                    buildQuestion(currentQuestions.get(index));
                    selectedIndex = -1;
                    scores = new int[numDisorders];
                }else{
                    ((LinearLayout) findViewById(R.id.questionnaire_layout)).removeView(optiongroup);
                    ((RelativeLayout) findViewById(R.id.button_layout)).removeView(findViewById(R.id.next_button));
                    if(scores[topIndices[0]] > THRESHOLD){
                        question_title.setText("Our results show that you may have these conditions. Click on one of them to get some information. Remember to consult with a doctor to confirm what problems you may have.");
                        for(i = 0; i < 3; i++){
                            int in = topIndices[i];
                            if(scores[in] > THRESHOLD){
                                disorders[i].setText(getResources().getStringArray(R.array.disorders)[in]);
                                disorders[i].setVisibility(View.VISIBLE);
                            }
                        }
                    }else{
                        question_title.setText("Our results show that you don't seem to strongly have any mental disorder. However, our results are not clinically accurate and you should always contact a medical professional if you believe something is troubling you.");
                    }
                }
            }else{
                buildQuestion(currentQuestions.get(index));
                selectedIndex = -1;
            }
        }
    }

    public void selectDisorder(View view){
        Intent intent = new Intent(this, Disorder.class);
        intent.putExtra("com.hackathon.unpack.disorder", ((TextView) view).getText().toString());
        startActivity(intent);
    }

    private void buildQuestion(Question question){
        question_title.setText(question.getQuestion());
        setOptions(question.getOptions());
    }

    private void setOptions(String[] options){
        int i = 0;
        buttonIDs = new int[options.length];
        for(;i < options.length; i++){
            RadioButton button = new RadioButton(this);
            int id = View.generateViewId();
            buttonIDs[i] = id;

            button.setId(id);
            button.setText(options[i]);
            button.setOnClickListener(this::radioButtonClick);
            optiongroup.addView(button);
        }
    }

    public void radioButtonClick(View view){
        int id = view.getId();

        int i;
        for(i = 0; i < buttonIDs.length; i++){
            if(buttonIDs[i] == id) break;
        }

        selectedIndex = i;
    }

    private class Question{

        private String question;
        private String[] options;
        private double[][] optionScores;
        private Question dependence = null;

        public Question(String question, String[] options, double[][] optionScores){
            this.question = question;
            this.options = options;
            this.optionScores = optionScores;

            if(this.optionScores.length != this.options.length ||
                    this.optionScores[0].length != numDisorders)
                throw new RuntimeException("Formatting for questions built incorrectly. Exiting.");
        }

        public Question(String question, String[] options, double[][] optionScores, Question dependence){
            this.dependence = dependence;
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

        public double[][] getOptionScores(){
            return optionScores;
        }

        public void passDependence(Question dependence, double[] scores){
            if(this.dependence != null && dependence != null && this.dependence.equals(dependence)){
                int i = 0;
                for(;i < optionScores.length; i++){
                    int j = 0;
                    for(;j < optionScores[i].length; j++){
                        optionScores[i][j] *= scores[j];
                    }
                }
            }
        }
    }
}