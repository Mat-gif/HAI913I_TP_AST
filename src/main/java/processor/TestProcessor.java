package processor;

import java.io.IOException;

public class TestProcessor {

        public static void main(String[] args) throws IOException {

                MyProcessor processor = new MyProcessor(
                                "");
                processor.setParser(
                                "C:\\Users\\manil\\Desktop\\Master_ico\\Master__2\\HAI913I - Evolution et restructuration des logiciels\\Dev\\org.anonbnr.design_patterns");
                System.out.println(
                                "Le nombre de package de l'application est : " + processor.countPackagesInProject()
                                                + " packages.");

                System.out.println("Le nombre de méthodes est de : " + processor.countMethodInProject() + " méthodes.");
                // System.out.println("Le nombre de classe est de : " + processor.countClassesInProject() + " classes.");

                System.out.println("Le nombre de Méthodes en moyenne  : " + processor.meanMethodsPerClass() + " .");

                System.out.println("Le nombre de lignes de code est de : " + processor.countLinesCodeInProject()
                                + " lignes de code.");

                System.out.println("Les 10% des classes qui possèdent les plus grandes nombres de méthodes : "
                                + processor.getTopClassesByMethodsCount() + " .");
                System.out.println("Les 10% des classes qui possèdent les plus grandes nombres d'attributs : "
                                + processor.getTopClassesByFieldsCount() + " .");
                System.out.println("Testing" + processor.getTopClassByMethodsAndField());

                System.out.println("les classes qui possèdent 2 méthodes ou plus"
                                + processor.getTopClassWithXGivenMethods(2));

        }
}