package com.og.MainClasses;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XMLUpdate {
    public static void updater(String title) {
        String filePath = "src/com/og/XMLs/Books";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            List<Book> books = XMLReturn.returnBooksIn("Books", "book");
            int index = 0;
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getTitle().equals(title)) {
                    index = i;
                }
            }

            updateAmount(doc, index);


            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/com/og/XMLs/Books"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);


        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static void updateAmount(Document doc, int index) {
        NodeList books = doc.getElementsByTagName("book");
        Element book = null;

        book = (Element) books.item(index);
        Node book1 = book.getElementsByTagName("amount").item(0).getFirstChild();
        int a = Integer.parseInt(book1.getNodeValue()) - 1;
        book1.setNodeValue(Integer.toString(a));

    }
}
