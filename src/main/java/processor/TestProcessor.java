package processor;

import parsers.EclipseJDTParser;

public class TestProcessor {

    public static void main(String[] args) {

        EclipseJDTParser parser = new EclipseJDTParser("C:\\\\Users\\\\manil\\\\Desktop\\\\Master_ico\\\\Master__2\\\\HAI925I - Programmation avancée Java EE\\\\Beginning\\\\loum-maven-backend\\\\", "C:\\\\Program Files\\\\Java\\\\jre1.8.0_51\\\\lib\\\\rt.jar");
        MyProcessor processor = new MyProcessor(parser);
        processor.process();

      
    }

}
