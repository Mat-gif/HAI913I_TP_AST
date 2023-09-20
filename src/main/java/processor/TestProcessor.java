package processor;

import java.io.IOException;

import parsers.EclipseJDTParser;

public class TestProcessor {

    public static void main(String[] args) throws IOException {

        EclipseJDTParser parser = new EclipseJDTParser(
                "C:\\\\Users\\\\manil\\\\Desktop\\\\Master_ico\\\\Master__2\\\\HAI925I - Programmation avancée Java EE\\\\Beginning\\\\loum-maven-backend\\\\");
        MyProcessor processor = new MyProcessor(parser);
        processor.process();

    }

}
