package com.og.MainClasses;

import com.og.Controllers.AdminScreenController;
import com.og.Controllers.SellerScreenController;
import com.og.Controllers.StoreKeeperScreenController;
import com.og.FXMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.og.MainClasses.XMLGet.getBook;
import static com.og.MainClasses.XMLGet.getUser;

public class XMLReturn {


    public static List<Book> returnBooksInShop() {
        List<Book> bookList = returnBooksIn("Books", "book");
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

    public static List<Book> returnBooksIn(String file, String tag) {
        File xmlFile = new File(String.format("src/com/og/XMLs/%s", file));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        List<Book> bookList = new ArrayList<Book>();
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName(tag);


            for (int i = 0; i < nodeList.getLength(); i++) {
                bookList.add(getBook(nodeList.item(i)));
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return bookList;
    }


    public static List<User> returnUsers() {
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

    public static void validation(String login, String password) {
        User u = new User(login, password);
        List<User> userList = returnUsers();

        for (int i = 0; i < userList.size(); i++) {
            if (u.getLogin().equals(userList.get(i).getLogin()) && u.getPassword().equals(userList.get(i).getPassword())) {
                User.activeUser = userList.get(i);
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


    public static void cleanUp(String file,String tag) {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter
                    (String.format("src/com/og/XMLs/%s",file));
            pw = new PrintWriter (fw);
            pw.write(String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<%s>\n" +
                    "    \n" +
                    "</%s>",tag,tag));
            pw.close();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
