package processor;

import parsers.EclipseJDTParser;

public class TestProcessor {

    public static void main(String[] args) {

        EclipseJDTParser parser = new EclipseJDTParser(
                "C:\\\\Users\\\\manil\\\\Desktop\\\\Master_ico\\\\Master__2\\\\HAI925I - Programmation avancée Java EE\\\\Beginning\\\\loum-maven-backend\\\\");
        MyProcessor processor = new MyProcessor(parser, "");
        try {
            processor.process();
        } catch (Exception e) {
            System.out.println("Le chemin du projet n'est pas valide");
        }

    }

}
