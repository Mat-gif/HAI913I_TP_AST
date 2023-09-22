package processor;

import java.io.IOException;

import parsers.EclipseJDTParser;

public class TestProcessor {

    public static void main(String[] args) throws IOException {

        MyProcessor processor = new MyProcessor(
                "");
        processor.setParser(
                "C:\\\\\\\\Users\\\\\\\\manil\\\\\\\\Desktop\\\\\\\\Master_ico\\\\\\\\Master__2\\\\\\\\HAI925I - Programmation avancée Java EE\\\\\\\\Beginning\\\\\\\\loum-maven-backend\\\\\\");
        System.out.println(
                " Le  nombre de package de l'application est : " + processor.countPackagesInProject() + " packages.");

        System.out.println(processor.countMethodInProject());

    }
}