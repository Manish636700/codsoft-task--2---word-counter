import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;

public class WordCounter extends JFrame {
    private static final String STOPWORDS="stopwords.txt";
    private JTextArea inputTextArea;
    private JButton countButton;
    private JLabel totalCountLabel;
    private Set<String> stopWords;

    public WordCounter() {
        setTitle("Word Counter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,300);
        setLocationRelativeTo(null);
        initializeComponents();
        stopWords = loadStopWords();

        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextArea.getText();
                if(!inputText.isEmpty())
                {
                    countWords(inputText);
                }
                else
                {
                    JOptionPane.showMessageDialog(WordCounter.this, "please enter some text.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private void initializeComponents()
    {
        inputTextArea= new JTextArea();
       countButton = new JButton("count Words");

       JPanel mainPanel = new JPanel();
       mainPanel.setLayout(new BorderLayout());
       mainPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);
       mainPanel.add(countButton, BorderLayout.SOUTH);

       setContentPane(mainPanel);
    }

    private void countWords(String text)
    {
        String[] words = text.split("[\\s.?!:;]+");
        Map<String, Integer> wordCounts = new HashMap<>();

        for(String word : words)
        {
            word = word.toLowerCase();
            if(!stopWords.contains(word))
            {
                wordCounts.put(word, wordCounts.getOrDefault(word,0)+1);
            }
        }
        StringBuilder result = new StringBuilder();
        result.append("Word count:\n");
        for(Map.Entry<String, Integer>entry : wordCounts.entrySet()){
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        result.append("Total words: ").append(words.length).append("\n");
        result.append("unique words: ").append(wordCounts.size());

        JOptionPane.showMessageDialog(WordCounter.this, result.toString(), "word count Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private Set<String>loadStopWords()
    {
        Set<String>stopWordsSet = new HashSet<>();

        try{
            File stopWordsFile = new File(STOPWORDS);
            Scanner sc = new Scanner(stopWordsFile);
            while(sc.hasNextLine())
            {
                String word = sc.nextLine().trim().toLowerCase();
                stopWordsSet.add(word);

            }
        }catch  (FileNotFoundException e)
        {
            System.out.println("Stop words filew not found");
        }
        return stopWordsSet;
    }
    public static void main(String[] args)
    {

        System.out.println("Welcome to the word counter!");
        System.out.println("please enter the text or provide the path to a file :");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WordCounter wordCounter = new WordCounter();
                wordCounter.setVisible(true);
            }
        });
    }
}