package com.og.MainClasses;

import com.og.Controllers.AdminScreenController;
import com.og.Controllers.SellerScreenController;
import com.og.Controllers.StoreKeeperScreenController;
import com.og.FXMLHelper;
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
import java.util.ArrayList;
import java.util.List;

public class WorkWIthXML {

    private static User getUser(Node node) {
        User user = new User();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            user.setLogin(getTagValue("login", element));
            user.setPassword(getTagValue("password", element));
            user.setPosition(getTagValue("position", element));
            user.setFullName(getTagValue("fullName", element));
        }

        return user;
    }

    private static Book getBook(Node node) {
        Book book = new Book();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            book.setTitle(getTagValue("title", element));
            book.setAuthor(getTagValue("author", element));
            book.setGenre(getTagValue("genre", element));
            book.setAmount(Integer.parseInt(getTagValue("amount", element)));
            book.setInShop(Boolean.parseBoolean(getTagValue("inShop", element)));

        }

        return book;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public static List<Book> returnBooks() {
        File xmlFile = new File("src/com/og/XMLs/Books");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<Book> bookList = new ArrayList<Book>();
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("book");


            for (int i = 0; i < nodeList.getLength(); i++) {
                bookList.add(getBook(nodeList.item(i)));
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return bookList;
    }

    public static List<Book> returnBooksInShop() {
        List<Book> bookList = returnBooks();
        List<Book> bookInShopList = new ArrayList<Book>();
        try {
            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).isInShop()) {
                    bookInShopList.add(bookList.get(i));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return bookInShopList;
    }

    public static List<Book> returnBooksInStock() {
        File xmlFile = new File("src/com/og/XMLs/Stock");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<Book> bookList = new ArrayList<Book>();
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("book");


            for (int i = 0; i < nodeList.getLength(); i++) {
                bookList.add(getBook(nodeList.item(i)));
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return bookList;
    }

    public static List<Book> returnBooksInNeedIt() {
        File xmlFile = new File("src/com/og/XMLs/NeedIt");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<Book> bookList1 = new ArrayList<Book>();
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("need");


            for (int i = 0; i < nodeList.getLength(); i++) {
                bookList1.add(getBook(nodeList.item(i)));
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return bookList1;
    }


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
            newAmount.appendChild(document.createTextNode(""));
            newBook.appendChild(newAmount);

            Element newInShop = document.createElement("inShop");
            newInShop.appendChild(document.createTextNode(""));
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

    public static void updater(String title) {
        String filePath = "src/com/og/XMLs/Books";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            List<Book> books = returnBooks();
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


            Element newLeft = document.createElement("left");
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

    public static void addInNeedIt(String title,String author, String genre) {
        File xmlFile = new File("src/com/og/XMLs/NeedIt");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element nList = document.getDocumentElement();

            Element newNeed = document.createElement("need");

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
            newAmount.appendChild(document.createTextNode(Integer.toString(5)));
            newNeed.appendChild(newAmount);


            nList.appendChild(newNeed);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new File("src/com/og/XMLs/NeedIt"));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void addInDelivery(String title, String author,String genre) {
        File xmlFile = new File("src/com/og/XMLs/Delivery");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element nList = document.getDocumentElement();

            Element newDelivery = document.createElement("book");

            Element newTitle = document.createElement("title");
            newTitle.appendChild(document.createTextNode(title));
            newDelivery.appendChild(newTitle);

            Element newAuthor = document.createElement("author");
            newAuthor.appendChild(document.createTextNode(author));
            newDelivery.appendChild(newAuthor);

            Element newGenre = document.createElement("genre");
            newGenre.appendChild(document.createTextNode(genre));
            newDelivery.appendChild(newGenre);

            Element newInShop = document.createElement("inShop");
            newInShop.appendChild(document.createTextNode(Boolean.toString(false)));
            newDelivery.appendChild(newInShop);

            Element newAmount = document.createElement("amount");
            newAmount.appendChild(document.createTextNode(Integer.toString(5)));
            newDelivery.appendChild(newAmount);


            nList.appendChild(newDelivery);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(new File("src/com/og/XMLs/Delivery"));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<User> returnUsers() {
        File xmlFile = new File("src/com/og/XMLs/Users.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<User> userList = new ArrayList<User>();
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("user");
            for (int i = 0; i < nodeList.getLength(); i++) {
                userList.add(getUser(nodeList.item(i)));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return userList;
    }

    public void validation(String login, String password) {
        User u = new User(login, password);
        List<User> userList = returnUsers();

        for (int i = 0; i < userList.size(); i++) {
            if (u.getLogin().equals(userList.get(i).getLogin()) && u.getPassword().equals(userList.get(i).getPassword())) {
                u.activeUser = userList.get(i);
                if (userList.get(i).getPosition().equals("admin")) {
                    AdminScreenController adminScreenController = FXMLHelper.loadScreenReturnController("AdminScreen");
                    adminScreenController.showAllBooks();
                    adminScreenController.userInfo();
                } else if (userList.get(i).getPosition().equals("seller")) {
                    SellerScreenController sellerScreenController = FXMLHelper.loadScreenReturnController("SellerScreen");
                    sellerScreenController.userInfo();
                    sellerScreenController.showAllBooksInShop();
                } else if (userList.get(i).getPosition().equals("store keeper")) {
                    StoreKeeperScreenController storeKeeperScreenController = FXMLHelper.loadScreenReturnController("StoreKeeperScreen");
                    storeKeeperScreenController.showBooksInStock();
                    storeKeeperScreenController.showNeedItList();
                    storeKeeperScreenController.userInfo();
                }
            }
        }
    }

    public void addUser(String login, String password, String position, String fullName) {

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
