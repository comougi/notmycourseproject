package com.og.MainClasses;

import com.og.Controllers.AdminScreenController;
import com.og.FXMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XMLAdd {
    public static void addBook(String title, String author, String genre) {

        File xmlFile = new File("src/com/og/XMLs/Books");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element nList = document.getDocumentElement();

            Element newBook = document.createElement("book");

            Element newTitle = document.createElement("title");
            newTitle.appendChild(document.createTextNode(title));
            newBook.appendChild(newTitle);

            Element newAuthor = document.createElement("author");
            newAuthor.appendChild(document.createTextNode(author));
            newBook.appendChild(newAuthor);

            Element newGenre = document.createElement("genre");
            newGenre.appendChild(document.createTextNode(genre));
            newBook.appendChild(newGenre);

            Element newAmount = document.createElement("amount");
            newAmount.appendChild(document.createTextNode(Integer.toString(1)));
            newBook.appendChild(newAmount);

            Element newInShop = document.createElement("inShop");
            newInShop.appendChild(document.createTextNode(Boolean.toString(true)));
            newBook.appendChild(newInShop);


            nList.appendChild(newBook);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new File("src/com/og/XMLs/Books"));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);


            AdminScreenController adminScreenController = FXMLHelper.loadScreenReturnController("AdminScreen");
            adminScreenController.showAllBooks();
            adminScreenController.userInfo();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void addSale(String title, int amount) {

        File xmlFile = new File("src/com/og/XMLs/SaleInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element nList = document.getDocumentElement();

            Element newSale = document.createElement("book");

            Element newTitle = document.createElement("title");
            newTitle.appendChild(document.createTextNode(title));
            newSale.appendChild(newTitle);


            Element newLeft = document.createElement("amount");
            newLeft.appendChild(document.createTextNode(Integer.toString(amount - 1)));
            newSale.appendChild(newLeft);


            nList.appendChild(newSale);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new File("src/com/og/XMLs/SaleInfo.xml"));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void addIn(String title, String author, String genre, int amount, String file, String tag) {
        File xmlFile = new File(String.format("src/com/og/XMLs/%s", file));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element nList = document.getDocumentElement();

            Element newNeed = document.createElement(tag);

            Element newTitle = document.createElement("title");
            newTitle.appendChild(document.createTextNode(title));
            newNeed.appendChild(newTitle);

            Element newAuthor = document.createElement("author");
            newAuthor.appendChild(document.createTextNode(author));
            newNeed.appendChild(newAuthor);

            Element newGenre = document.createElement("genre");
            newGenre.appendChild(document.createTextNode(genre));
            newNeed.appendChild(newGenre);

            Element newInShop = document.createElement("inShop");
            newInShop.appendChild(document.createTextNode(Boolean.toString(false)));
            newNeed.appendChild(newInShop);

            Element newAmount = document.createElement("amount");
            newAmount.appendChild(document.createTextNode(Integer.toString(amount)));
            newNeed.appendChild(newAmount);


            nList.appendChild(newNeed);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new File(String.format("src/com/og/XMLs/%s", file)));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public static void addFromStockToShop() {
        List<Book> deliveryBooks = XMLReturn.returnBooksIn("Delivery", "book");
        for (int i = 0; i < deliveryBooks.size(); i++) {
            addBook(deliveryBooks.get(i).getTitle(), deliveryBooks.get(i).getAuthor(), deliveryBooks.get(i).getGenre());
        }


    }

    public static void addUser(String login, String password, String position, String fullName) {

        File xmlFile = new File("src/com/og/XMLs/Users.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element nList = document.getDocumentElement();

            Element newUser = document.createElement("user");

            Element newLogin = document.createElement("login");
            newLogin.appendChild(document.createTextNode(login));
            newUser.appendChild(newLogin);

            Element newPassword = document.createElement("password");
            newPassword.appendChild(document.createTextNode(password));
            newUser.appendChild(newPassword);

            Element newPosition = document.createElement("position");
            newPosition.appendChild(document.createTextNode(position));
            newUser.appendChild(newPosition);

            Element newFullName = document.createElement("fullName");
            newFullName.appendChild(document.createTextNode(fullName));
            newUser.appendChild(newFullName);

            nList.appendChild(newUser);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new File("src/com/og/XMLs/Users.xml"));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);


            FXMLHelper.loadScreen("StartScreen");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
