package main;

import javax.swing.SwingUtilities;

/*
 * @author Kasper Radom 264023
 *
 * budowanie:
 * 	dir /b /s *.java > sources.txt
 * kompilacj:
 * 	javac -d bin @sources.txt
 * archiwizacja:
 * 	xcopy src bin /e
 * 	java csv lab05_pop.jar -C bin .
 * uruchamianie:
 * 	java -jar lab05_pop.jar
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame();
            }
        });

    }
}