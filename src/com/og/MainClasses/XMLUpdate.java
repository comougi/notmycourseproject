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
    public static void amountUpdater(String title,String file) {
        String filePath = String.format("src/com/og/XMLs/%s",file);
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            List<Product> products = XMLReturn.returnProductsIn("Products", "product");
            int index = 0;
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getModel().equals(title)) {
                    index = i;
                }
            }

            updateAmount(doc, index);


            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/com/og/XMLs/Products"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);


        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static void updateAmount(Document doc, int index) {
        NodeList books = doc.getElementsByTagName("product");
        Element book = null;

        book = (Element) books.item(index);
        Node book1 = book.getElementsByTagName("amount").item(0).getFirstChild();
        int a = Integer.parseInt(book1.getNodeValue()) - 1;
        book1.setNodeValue(Integer.toString(a));

    }
}
